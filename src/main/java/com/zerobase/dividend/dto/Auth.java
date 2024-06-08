package com.zerobase.dividend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class Auth {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class SignUp{
        private String username;
        private String password;
        private List<String> roles;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class SignIn{
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }
}
