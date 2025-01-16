package com.pefonseca.library.api.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UserDTO(
        @NotBlank(message = "campo obrigatório")
        String login,
        @Email(message = "email inválido")
        @NotBlank(message = "campo obrigatório")
        String email,
        @NotBlank(message = "campo obrigatório")
        String password,
        List<String> roles) {
}
