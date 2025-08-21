package com.pettrek.backend.passport.dto;

import com.pettrek.backend.passport.models.Pet;
import com.pettrek.backend.auth.models.User;
import java.time.LocalDate;
import java.util.List;

public record PassportResponse(
        String name,
        String secondName,
        String surname,
        LocalDate dateOfBirth,
        String email,
        List<PetDto> pets

){}
