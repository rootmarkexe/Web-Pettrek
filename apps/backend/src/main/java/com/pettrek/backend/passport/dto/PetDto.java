package com.pettrek.backend.passport.dto;

import com.pettrek.backend.auth.models.User;
import com.pettrek.backend.passport.models.Pet;
import jakarta.persistence.Column;

import java.time.LocalDate;

public record PetDto(
        String name,

        String specie,

        String gender,

        LocalDate dateOfBirth,

        String breed,

        String hair,

        Double weight,

        String feature,

        User user
){}
