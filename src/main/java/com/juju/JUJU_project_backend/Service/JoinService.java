package com.juju.JUJU_project_backend.Service;

import com.juju.JUJU_project_backend.DTO.VerifyEmailDto;
import com.juju.JUJU_project_backend.DTO.JoinDto;
import com.juju.JUJU_project_backend.Entity.Events;
import com.juju.JUJU_project_backend.Entity.MainOption;
import com.juju.JUJU_project_backend.Entity.Token;
import com.juju.JUJU_project_backend.Repository.EventsRepository;
import com.juju.JUJU_project_backend.Repository.MainOptionRepository;
import com.juju.JUJU_project_backend.Repository.TokenRepository;
import com.juju.JUJU_project_backend.Utill.EncryptionUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@Transactional
@Log4j2
public class JoinService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private MainOptionRepository mainOptionRepository;

    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EncryptionUtil encryptionUtil;

    public String registerUser(JoinDto joinDto) throws Exception {
        log.info("registerUser called with: {}", joinDto);

        // 비밀번호 일치 여부 확인
        if (!joinDto.getPassword().equals(joinDto.getPasswordConfirm())) {
            log.error("Passwords do not match for email: {}", joinDto.getEmail());
            throw new Exception("Passwords do not match");
        }

        Optional<Token> existingToken = tokenRepository.findByEmail(joinDto.getEmail());
        log.info("Existing token: {}", existingToken);
        if (existingToken.isPresent() && existingToken.get().isEmailVerified()) {
            Token token = existingToken.get();
            token.setUsername(joinDto.getFullName());
            token.setNickname(joinDto.getNickname());
            token.setPassword(passwordEncoder.encode(joinDto.getPassword()));
            token.setBirthday(LocalDate.parse(joinDto.getBirthDate()));
            token.setVerificationToken(null);
            tokenRepository.save(token);

            MainOption mainOption = new MainOption();
            mainOption.setEmail(joinDto.getEmail());
            mainOption.setUsername(joinDto.getFullName());
            mainOption.setNickname(joinDto.getNickname());
            mainOption.setPassword(passwordEncoder.encode(joinDto.getPassword()));
            mainOption.setBirthday(LocalDate.parse(joinDto.getBirthDate()));
            mainOptionRepository.save(mainOption);

            Events event = new Events();
            event.setEmail(joinDto.getEmail());
            event.setTitle("Default Event");
            event.setStart(LocalDate.now().atStartOfDay());
            event.setEnd(LocalDate.now().atStartOfDay().plusHours(1));
            event.setColor("#FFFFFF");
            eventsRepository.save(event);

            log.info("User registered successfully with email: {}", joinDto.getEmail());
            return encryptionUtil.encrypt(joinDto.getEmail());
        } else {
            log.error("Email not verified for email: {}", joinDto.getEmail());
            throw new IllegalArgumentException("Email not verified");
        }
    }
    @SneakyThrows
    @Async
    public void sendEmailVerification(String email) throws Exception {
        log.info("sendEmailVerification called with email: {}", email);
        Token token = tokenRepository.findById(email)
                .orElse(Token.builder()
                        .email(email)
                        .verificationToken(generateVerificationCode())
                        .emailVerified(false)
                        .build());

        if (token.isEmailVerified()) {
            throw new IllegalArgumentException("Email is already verified.");
        }

        token.setVerificationToken(generateVerificationCode());
        tokenRepository.save(token);

        try {
            sendVerificationEmail(token);
        } catch (MessagingException e) {
            log.error("Error sending verification email: ", e);
            throw new Exception("Failed to send verification email", e);
        }
    }

    private void sendVerificationEmail(Token token) throws MessagingException {
        String subject = "Please verify your email";
        String senderName = "Your App Name";
        String verificationCode = token.getVerificationToken();
        String mailContent = "<p>Dear User,</p>";
        mailContent += "<p>Your verification code is: " + verificationCode + "</p>";
        mailContent += "<p>Please enter this code in the verification field.</p>";
        mailContent += "<p>Thank you<br>Your App Name</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("commonusers2024@gmail.com", senderName);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        helper.setTo(token.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);

        mailSender.send(message);
    }

    private String generateVerificationCode() {
        String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            code.append(alphanumeric.charAt(random.nextInt(alphanumeric.length())));
        }
        return code.toString();
    }

    public void verifyUser(String encryptedToken) throws Exception {
        try {
            String token = encryptionUtil.decrypt(encryptedToken);
            Optional<Token> userOptional = tokenRepository.findByVerificationToken(token);
            if (userOptional.isPresent()) {
                Token user = userOptional.get();
                user.setEmailVerified(true);
                user.setVerificationToken(null);
                tokenRepository.save(user);
                log.info("User email verified successfully for token: {}", token);
            } else {
                throw new IllegalArgumentException("Invalid verification token");
            }
        } catch (UnsupportedEncodingException e) {
            log.error("Error decrypting token: ", e);
            throw new Exception("Invalid verification token", e);
        }
    }

    public boolean verifyEmailCode(VerifyEmailDto verifyEmailDto) {
        log.info("verifyEmailCode called with: {}", verifyEmailDto);
        Optional<Token> userOptional = tokenRepository.findById(verifyEmailDto.getEmail());
        if (userOptional.isPresent()) {
            Token user = userOptional.get();
            if (user.getVerificationToken() != null &&
                    user.getVerificationToken().equalsIgnoreCase(verifyEmailDto.getCode())) {
                user.setEmailVerified(true);
                user.setVerificationToken(null);
                tokenRepository.save(user);
                log.info("Email code verified successfully for email: {}", verifyEmailDto.getEmail());
                return true;
            }
        }
        log.error("Invalid verification code for email: {}", verifyEmailDto.getEmail());
        return false;
    }
}


