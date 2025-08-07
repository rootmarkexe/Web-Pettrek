package com.pettrek.backend.passport.services;

import com.pettrek.backend.auth.models.User;
import com.pettrek.backend.auth.repos.UserRepo;
import com.pettrek.backend.auth.security.UserPrincipal;
import com.pettrek.backend.passport.dto.PassportResponse;
import com.pettrek.backend.passport.dto.PetDto;
import com.pettrek.backend.passport.mapper.PassportMapper;
import com.pettrek.backend.passport.mapper.PetMapper;
import com.pettrek.backend.passport.models.Pet;
import com.pettrek.backend.passport.repos.PassportRepo;
import com.pettrek.backend.passport.repos.PetRepo;
import com.pettrek.backend.passport.services.impl.PetPassportService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PetPassportServiceImpl implements PetPassportService {
    private final UserContextService userContextService;
    private PassportRepo passportRepo;
    private final ModelMapper modelMapper;
    private PetRepo petRepo;
    private final UserRepo userRepo;

    @Transactional
    public PassportResponse getUserWithPets(){
        User user = userContextService.getCurrentUserWithPets();
        return PassportMapper.fromEntity(user);
    }

    @Transactional
    public Pet createPetPassport(PetDto petDto){
        User user = userContextService.getCurrentUser();
        Integer lastPassportNumber = petRepo.findMaxPassportNumberByUser(petDto.user())
                .orElse(0);

        Pet pet = PetMapper.toEntity(petDto);
        pet.setPassportNumber(++lastPassportNumber);
        Pet result = petRepo.save(pet);
        return result;
    }
    @Transactional
    public Pet updatePetPassport(Integer passportNumber, PetDto petDto){
        User user = userContextService.getCurrentUser();
        Pet pet = user.getPets().stream()
                .filter(p -> p.getPassportNumber().equals(passportNumber))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Такого паспорта нет"));

        modelMapper.getConfiguration()
                .setSkipNullEnabled(true);
        modelMapper.map(petDto, pet);
        return petRepo.save(pet);
    }
}


