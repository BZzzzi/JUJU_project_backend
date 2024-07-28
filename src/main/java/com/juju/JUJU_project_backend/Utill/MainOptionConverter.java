package com.juju.JUJU_project_backend.Utill;

import com.juju.JUJU_project_backend.DTO.*;
import com.juju.JUJU_project_backend.Entity.MainOption;

public class MainOptionConverter {

    public static JoinDto toJoinDto(MainOption mainOption) {
        JoinDto dto = new JoinDto();
        dto.setEmail(mainOption.getEmail());
        dto.setUsername(mainOption.getUsername());
        dto.setPassword1(mainOption.getPassword());
        dto.setPassword2(mainOption.getPassword());
        dto.setNickname(mainOption.getNickname());
        dto.setBirthday(mainOption.getBirthday());
        return dto;
    }

    public static MainOption toMainOptionFromJoinDto(JoinDto dto) {
        MainOption mainOption = new MainOption();
        mainOption.setEmail(dto.getEmail());
        mainOption.setUsername(dto.getUsername());
        mainOption.setPassword(dto.getPassword1());
        mainOption.setNickname(dto.getNickname());
        mainOption.setBirthday(dto.getBirthday());
        return mainOption;
    }

    public static LoginRequestDto toLoginRequestDto(MainOption mainOption) {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail(mainOption.getEmail());
        dto.setPassword(mainOption.getPassword());
        return dto;
    }

    public static MainOption toMainOptionFromLoginRequestDto(LoginRequestDto dto) {
        MainOption mainOption = new MainOption();
        mainOption.setEmail(dto.getEmail());
        mainOption.setPassword(dto.getPassword());
        return mainOption;
    }

    public static LoginResponseDto toLoginResponseDto(MainOption mainOption) {
        LoginResponseDto dto = new LoginResponseDto();
        dto.setEmail(mainOption.getEmail());
        dto.setUsername(mainOption.getUsername());
        dto.setNickname(mainOption.getNickname());
        dto.setProfileImgPath(mainOption.getProfile_img_path());
        return dto;
    }

    public static NicknameDto toNicknameDto(MainOption mainOption) {
        NicknameDto dto = new NicknameDto();
        dto.setEmail(mainOption.getEmail());
        dto.setNickname(mainOption.getNickname());
        return dto;
    }

    public static MainOption toMainOptionFromNicknameDto(NicknameDto dto) {
        MainOption mainOption = new MainOption();
        mainOption.setEmail(dto.getEmail());
        mainOption.setNickname(dto.getNickname());
        return mainOption;
    }

    public static PasswordChangeDto toPasswordChangeDto(MainOption mainOption) {
        PasswordChangeDto dto = new PasswordChangeDto();
        dto.setEmail(mainOption.getEmail());
        return dto;
    }

    public static MainOption toMainOptionFromPasswordChangeDto(PasswordChangeDto dto) {
        MainOption mainOption = new MainOption();
        mainOption.setEmail(dto.getEmail());
        mainOption.setPassword(dto.getNewPassword());
        return mainOption;
    }

    public static ProfileDto toProfileDto(MainOption mainOption) {
        ProfileDto dto = new ProfileDto();
        dto.setEmail(mainOption.getEmail());
        dto.setProfileImgName(mainOption.getProfile_img_name());
        dto.setProfileImgPath(mainOption.getProfile_img_path());
        return dto;
    }

    public static MainOption toMainOptionFromProfileDto(ProfileDto dto) {
        MainOption mainOption = new MainOption();
        mainOption.setEmail(dto.getEmail());
        mainOption.setProfile_img_name(dto.getProfileImgName());
        mainOption.setProfile_img_path(dto.getProfileImgPath());
        return mainOption;
    }
}
