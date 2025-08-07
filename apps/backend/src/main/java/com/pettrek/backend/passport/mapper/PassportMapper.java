package com.pettrek.backend.passport.mapper;

import com.pettrek.backend.auth.models.User;
import com.pettrek.backend.passport.dto.PassportResponse;
import com.pettrek.backend.passport.dto.PetDto;

public class PassportMapper {
    public static PassportResponse fromEntity(User user){
        return new PassportResponse(
                user.getName(),
                user.getSecondName(),
                user.getSurname(),
                user.getDateOfBirth(),
                user.getEmail(),
                user.getPets().stream()
                        .map(p -> new PetDto(
                                p.getName(),
                                p.getSpecie(),
                                p.getGender(),
                                p.getDateOfBirth(),
                                p.getBreed(),
                                p.getHair(),
                                p.getWeight(),
                                p.getFeature(),
                                p.getUser()
                        ))
                        .toList()
        );
    }
}
