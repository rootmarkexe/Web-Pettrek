package com.pettrek.backend.passport.controller;

import com.pettrek.backend.auth.dto.ApiResponses;
import com.pettrek.backend.auth.models.User;
import com.pettrek.backend.auth.repos.UserRepo;
import com.pettrek.backend.auth.security.UserPrincipal;
import com.pettrek.backend.passport.dto.PassportResponse;
import com.pettrek.backend.passport.dto.PetDto;
import com.pettrek.backend.passport.models.Pet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.pettrek.backend.passport.services.PassportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@RestController
@RequestMapping("api/passport")
@RequiredArgsConstructor
@Tag(name = "Паспорта животных", description = "Управление паспортами питомцев")
public class PassportController {

    private final IPassportService passportService;

    @GetMapping("/GetPassport")
    @Operation(
            summary = "Получить паспорт",
            description = "Возвращает данные пользователя с информацией о питомцах"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Успешное получение данных",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                {
                    "name": "Иван",
                    "secondName": "Иванович",
                    "surname": "Иванов",
                    "dateOfBirth": "1990-01-01",
                    "email": "user@example.com",
                    "pets": [
                        {
                            "name": "Барсик",
                            "specie": "Кошка",
                            "gender": "Мужской",
                            "dateOfBirth": "2020-05-15",
                            "breed": "Британская",
                            "hair": "Короткошерстный",
                            "weight": 4.5,
                            "feature": "Любит спать"
                        }
                    ]
                }
                """
                    )
                    ))
    public ResponseEntity<?> getPassport(){
        PassportResponse userWithPets = passportService.getUserWithPets();
        return ResponseEntity.ok(userWithPets);
    }
    @Operation(
            summary = "Создать паспорт питомца",
            description = "Добавляет нового питомца в систему"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Данные питомца",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PetDto.class),
                    examples = @ExampleObject(
                            name = "Пример запроса",
                            value = """
                    {
                        "name": "Барсик",
                        "specie": "Кошка",
                        "gender": "Мужской",
                        "dateOfBirth": "2020-05-15",
                        "breed": "Британская",
                        "hair": "Короткошерстный",
                        "weight": 4.5,
                        "feature": "Любит спать"
                    }
                    """
                    )
            )
    )

            @ApiResponse(responseCode = "200", description = "Питомец добавлен", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponses.class),
                    examples = @ExampleObject(
                            value = """
                       {
                           "status": 201,
                           "message": "Питомец добавлен"
                       }
                       """
                    )
            ))
    @PostMapping("/create-passport")
    public ResponseEntity<?> createPetPassport(@Valid @RequestBody PetDto petDto){
        Pet pet = passportService.createPetPassport(
            petDto
        );
        return new ResponseEntity<>(new ApiResponses(201, "Питомец добавлен"), HttpStatus.CREATED);
    }

    @PatchMapping("/edit-passport/{passportNumber}")
    public ResponseEntity<?> editPetPassport(@Valid @RequestBody PetDto petDto, @PathVariable Integer passportNumber){
        Pet updatePet = passportService.updatePet(petDto, passportNumber);
        return ResponseEntity.noContent().build();
    }
}
