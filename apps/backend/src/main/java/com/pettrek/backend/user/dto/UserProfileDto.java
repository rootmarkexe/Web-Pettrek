package com.pettrek.backend.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public record UserProfileDto(

        @NotBlank
        String name,

        @NotBlank
        String secondName,

        String surname,

        @NotBlank
        String city,

        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dateOfBirth
){}
