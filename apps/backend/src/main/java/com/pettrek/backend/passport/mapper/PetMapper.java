package com.pettrek.backend.passport.mapper;

import com.pettrek.backend.passport.dto.PetDto;
import com.pettrek.backend.passport.models.Pet;

public class PetMapper {
    public static Pet toEntity(PetDto petDto){
        if(petDto == null) return null;

        Pet pet = new Pet();
        pet.setName(petDto.name());
        pet.setSpecie(petDto.specie());
        pet.setGender(petDto.gender());
        pet.setDateOfBirth(petDto.dateOfBirth());
        pet.setBreed(petDto.breed());
        pet.setHair(petDto.hair());
        pet.setWeight(petDto.weight());
        pet.setFeature(petDto.feature());
        return pet;
    }
}
