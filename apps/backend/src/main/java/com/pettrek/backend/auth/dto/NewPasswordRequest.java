package com.pettrek.backend.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewPasswordRequest(

    @NotBlank
    String token,

    @NotBlank
    @Size(min = 6, max = 30)
    String newPassword
){}
