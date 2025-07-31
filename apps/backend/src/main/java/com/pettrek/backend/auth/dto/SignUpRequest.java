package com.pettrek.backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
public record SignUpRequest(
    @NotBlank
    String name,

    @NotBlank
    String secondName,

    String surname,

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dateOfBirth,

    @NotBlank
    @Email
    String email,

    @NotBlank
    @Size(min = 6, max = 30)
    String password
){}
