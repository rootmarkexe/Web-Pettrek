package com.pettrek.backend.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(

    @NotBlank
    String email,

    @NotBlank
    @Size(min = 6, max = 30)
    String password
){}
