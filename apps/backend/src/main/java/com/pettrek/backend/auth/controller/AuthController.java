package com.pettrek.backend.auth.controller;

import com.pettrek.backend.auth.dto.*;
import com.pettrek.backend.auth.models.User;
import com.pettrek.backend.auth.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth/")
@Tag(name= "Аутентификация", description = "")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    @Operation(summary = "Логин", description = "Возвращает access и refresh токены")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Данные для входа",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LoginRequest.class),
                    examples = {
                            @ExampleObject(
                                    name = "Пример запроса",
                                    value = "{\"email\": \"user@example.com\", \"password\": \"password123!\"}",
                                    summary = "Стандартный вход"
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "200",
            description = "Успешная аутентификация",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthTokensResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Невалидный email",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Тело ответа",
                                    value = """
                                            {
                                                "status": 400,
                                                "message": "email: должно иметь формат адреса электронной почты",
                                                "timestamp": "2025-07-29T15:11:40.3309628"
                                            }
                                            """
                            )
                    }))
    @ApiResponse(
            responseCode = "400",
            description = "Невалидный пароль",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Тело ответа",
                                    value = """
                                            {
                                                "status": 400,
                                                "message": "password: размер должен находиться в диапазоне от 6 до 30",
                                                "timestamp": "2025-07-29T15:11:40.3309628"
                                            }
                                            """
                            )
                    }))
    @ApiResponse(
            responseCode = "401",
            description = "Неверный email или пароль",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Тело ответа",
                                    value = """
                                            {
                                                "status": 401,
                                                "message": "Неверный email или пароль",
                                                "timestamp": "2025-07-29T15:11:40.3309628"
                                            }
                                            """
                            )
                    })

    )
    @ApiResponse(
            responseCode = "500",
            description = "Ошибка сервера",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Тело ответа",
                                    value = """
                                            {
                                                "status": 500,
                                                "message": "Внутренняя ошибка сервера",
                                                "timestamp": "2025-07-29T15:11:40.3309628"
                                            }
                                            """
                            )
                    })
    )
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        AuthTokensResponse tokens = authService.authenticateUser(loginRequest.email(),loginRequest.password());
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/signup")
    @Operation(summary = "Регистрация", description = "Создание нового пользователя")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Данные для входа",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LoginRequest.class),
                    examples = {
                            @ExampleObject(
                                    name = "Пример запроса",
                                    value = "{\"email\": \"user@example.com\", \"password\": \"password123!\"}",
                                    summary = "Новый пользователь"
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "200",
            description = "Успешная аутентификация",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Тело ответа",
                                    value = """
                                            {
                                                "status": 200,
                                                "message": "Проверьте вашу почту"
                                            }
                                            """
                            )
                    })
    )
    @ApiResponse(
            responseCode = "400",
            description = "Невалидные данные",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Тело ответа",
                                    value = """
                                            {
                                                "status": 400,
                                                "message": "Неверный формат email"
                                            }
                                            """
                            )
                    })
    )
    @ApiResponse(
            responseCode = "409",
            description = "Такой email уже существует",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Тело ответа",
                                    value = """
                                            {
                                                "status": 409,
                                                "message": "Такой email уже существует"
                                            }
                                            """
                            )
                    })
    )
    @ApiResponse(
            responseCode = "500",
            description = "Внутренняя ошибка сервера",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Тело ответа",
                                    value = """
                                            {
                                                "status": 500,
                                                "message": "Внутренняя ошибка сервера"
                                            }
                                            """
                            )
                    })
    )
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest){
        User user = authService.registerUser(
                signUpRequest.email(),
                signUpRequest.password(),
                signUpRequest.name(),
                signUpRequest.secondName(),
                signUpRequest.surname(),
                signUpRequest.dateOfBirth()
        );
        return ResponseEntity.ok(new ApiResponses(200, "Проверьте вашу почту"));
    }
    @GetMapping("/verify")
    @Operation(summary = "Верификация email", description = "Подтверждает email пользователя по коду")
    @ApiResponse(
            responseCode = "200",
            description = "Email успешно подтвержден",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthTokensResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Неверный код подтверждения",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Тело ответа",
                                    value = """
                                            {
                                                "status": 400,
                                                "message": "Неверный код подтверждения"
                                            }
                                            """
                            )
                    })
    )
    @ApiResponse(
            responseCode = "404",
            description = "Код подтверждения не найден",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Тело ответа",
                                    value = """
                                            {
                                                "status": 404,
                                                "message": "Код подтверждения не найден"
                                            }
                                            """
                            )
                    })
    )
    @ApiResponse(
            responseCode = "500",
            description = "Внутренняя ошибка сервера",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Тело ответа",
                                    value = """
                                            {
                                                "status": 500,
                                                "message": "Внутренняя ошибка сервера"
                                            }
                                            """
                            )
                    })
    )
    public ResponseEntity<?> verifyEmail(@RequestParam String code){
        AuthTokensResponse tokens = authService.verifyEmail(code);
        return ResponseEntity.ok(tokens);
    }
    @PostMapping("/password/reset-request")
    @ApiResponse(
            responseCode = "200",
            description = "Ссылка для сброса пароля отправлена",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class, example = "Ссылка для сброса пароля отправлена на почту")
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Невалидный email",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Тело ответа",
                                    value = """
                                            {
                                                "status": 400,
                                                "message": "Невалидный email"
                                            }
                                            """
                            )
                    })
    )
    @ApiResponse(
            responseCode = "404",
            description = "Пользователь с таким email не найден",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Тело ответа",
                                    value = """
                                            {
                                                "status": 404,
                                                "message": "Пользователь с таким email не найден"
                                            }
                                            """
                            )
                    })
    )
    @ApiResponse(
            responseCode = "500",
            description = "Внутренняя ошибка сервера",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Тело ответа",
                                    value = """
                                            {
                                                "status": 500,
                                                "message": "Внутренняя ошибка сервера"
                                            }
                                            """
                            )
                    })
    )
    public ResponseEntity<?> requestPasswordReset(@Valid @RequestBody PasswordResetRequest passwordResetRequest) {
        authService.requestPasswordReset(passwordResetRequest.email());
        ApiResponses response = new ApiResponses(200, "Ссылка для сброса пароля отправлена на почту");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/password/reset")
    @Operation(summary = "Сброс пароля", description = "Устанавливает новый пароль по токену")
    @ApiResponse(
            responseCode = "200",
            description = "Пароль успешно изменен",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthTokensResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Невалидный код или пароль",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Тело ответа",
                                    value = """
                                            {
                                                "status": 400,
                                                "message": "Невалидный код или пароль"
                                            }
                                            """
                            )
                    })
    )
    @ApiResponse(
            responseCode = "404",
            description = "Токен сброса пароля не найден",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Тело ответа",
                                    value = """
                                            {
                                                "status": 404,
                                                "message": "Код сброса пароля не найден"
                                            }
                                            """
                            )
                    })
    )
    @ApiResponse(
            responseCode = "500",
            description = "Внутренняя ошибка сервера",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Тело ответа",
                                    value = """
                                            {
                                                "status": 500,
                                                "message": "Внутренняя ошибка сервера"
                                            }
                                            """
                            )
                    })
    )
    public ResponseEntity<?> resetPassword(@Valid @RequestBody NewPasswordRequest newPasswordRequest) {
        AuthTokensResponse tokens = authService.resetPassword(
                newPasswordRequest.token(),
                newPasswordRequest.newPassword()
        );
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Обновление токена", description = "Возвращает новую пару access и refresh токенов")
    @ApiResponse(
            responseCode = "200",
            description = "Токены успешно обновлены",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RefreshTokenRequest.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Невалидный refresh token",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Тело ответа",
                                    value = """
                                            {
                                                "status": 400,
                                                "message": "Невалидный refresh token"
                                            }
                                            """
                            )
                    })
    )
    @ApiResponse(
            responseCode = "401",
            description = "Refresh token недействителен",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Тело ответа",
                                    value = """
                                            {
                                                "status": 401,
                                                "message": "Невалидный refresh token"
                                            }
                                            """
                            )
                    })

    )
    @ApiResponse(
            responseCode = "500",
            description = "Внутренняя ошибка сервера",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Тело ответа",
                                    value = """
                                            {
                                                "status": 500,
                                                "message": "Внутренняя ошибка сервера"
                                            }
                                            """
                            )
                    })
    )
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        AuthTokensResponse tokens = authService.refreshToken(refreshTokenRequest.refreshToken());
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/logout")
    @Operation(summary = "Выход из системы", description = "Уничтожает refresh token")
    @ApiResponse(
            responseCode = "200",
            description = "Успешный выход из системы",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class, example = "Пользователь успешно вышел!")
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Невалидный Refresh token",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Тело ответа",
                                    value = """
                                            {
                                                "status": 500,
                                                "message": "Невалидный Refresh token"
                                            }
                                            """
                            )
                    })
    )
    @ApiResponse(
            responseCode = "401",
            description = "Refresh token не предоставлен",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Тело ответа",
                                    value = """
                                            {
                                                "status": 401,
                                                "message": "Refresh token не предоставлен"
                                            }
                                            """
                            )
                    })
    )
    @ApiResponse(
            responseCode = "500",
            description = "Внутренняя ошибка сервера",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "Тело ответа",
                                    value = """
                                            {
                                                "status": 500,
                                                "message": "Внутренняя ошибка сервера"
                                            }
                                            """
                            )
                    })
    )
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        String refreshToken = authHeader.substring(7);
        authService.logout(refreshToken);
        ApiResponses response = new ApiResponses(200,"Пользователь успешно вышел!");
        return ResponseEntity.ok(response);
    }

}
