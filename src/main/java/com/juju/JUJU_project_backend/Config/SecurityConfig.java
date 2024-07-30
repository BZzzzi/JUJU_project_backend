package com.juju.JUJU_project_backend.Config;

import com.juju.JUJU_project_backend.Service.LoginDetailService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private LoginDetailService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(withDefaults())  // CORS 설정 추가
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/login", "/api/register",
                                        "/api/verify/{encryptedToken}", "/api/send-email-verification",
                                        "/api/verify-email-code", "/api/uploadProfilePicture", "/file/**",
                                        "/api/changeNickname", "/api/main", "/api/todo", "/api/todo/add",
                                        "/api/todo/update", "/api/todo/delete").permitAll()
                                .requestMatchers("/api/changePassword").authenticated()
                                .anyRequest().authenticated()
                )
                .formLogin(login -> login.disable())  // 기본 폼 로그인 비활성화
                .httpBasic(withDefaults())  // HTTP Basic 인증 활성화 (선택사항)
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("Unauthorized");
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.getWriter().write("Logged out successfully");
                        })
                )
                .userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}