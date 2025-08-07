package com.pettrek.backend.passport.services.impl;

import com.pettrek.backend.passport.dto.PassportResponse;
import com.pettrek.backend.passport.dto.PetDto;
import com.pettrek.backend.passport.models.Pet;

public interface PetPassportService {
    PassportResponse getUserWithPets();
    Pet createPetPassport(PetDto petDto);
    Pet updatePetPassport(Integer passportNumber, PetDto petDto);
}
