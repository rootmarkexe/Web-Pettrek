package com.pettrek.backend.user.controllers;

import com.pettrek.backend.auth.dto.ApiResponses;
import com.pettrek.backend.auth.dto.LoginRequest;
import com.pettrek.backend.user.dto.UserProfileDto;
import com.pettrek.backend.user.services.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-profile")
@Tag(name="Первичное создание профиля", description = "После регистрация идет инициализация пользователя, он заполняет ФИО, место проживания, дату рождения")
public class UserProfileController {
    private final UserProfileService userProfileService;

    UserProfileController(UserProfileService userProfileService){
        this.userProfileService = userProfileService;
    }

    @PostMapping("")
    @Operation(summary = "Первичное создание пользователя")
    @Parameter(
            name = "Authorization",
            description = "Bearer токен для авторизации",
            required = true,
            in = ParameterIn.HEADER,
            example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Данные для создания профиля",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LoginRequest.class),
                    examples = {
                            @ExampleObject(
                                    name = "Пример запроса",
                                    value = "{\"secondName\": \"Теплов\", \"name\": \"Иван\", \"surname\": \"Андреевич\", \"dateOfBirth\": \"2005-08-26\", \"city\": \"Кемерово\"}",
                                    summary = "Стандартное создание провиля"
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "201",
            description = "Пользователь успешно создан"
    )
    public ResponseEntity<?> fullSignUp(@Valid @RequestBody UserProfileDto userProfileDto){
        userProfileService.initUserProfile(userProfileDto);
        return ResponseEntity.ok(new ApiResponses(200, "Пользователь инициализирован"));
    }
}
