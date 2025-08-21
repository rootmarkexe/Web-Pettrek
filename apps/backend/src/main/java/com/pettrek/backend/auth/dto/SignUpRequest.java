package com.pettrek.backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
public record SignUpRequest(
    @NotBlank
    @Email
    String email,

    @NotBlank
    @Size(min = 6, max = 30)
    String password
){}
