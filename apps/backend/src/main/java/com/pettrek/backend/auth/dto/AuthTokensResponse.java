package com.pettrek.backend.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

public record AuthTokensResponse (

    @Schema(description = "Access token для авторизации", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzUzNjIwMTExLCJleHAiOjE3NTM2MjA1OTF9.lwJrcC5Ck7MDhk9nXNi4zK_QPkwh1DVqwpvN_2sJ92kd6r1_VxKx8P3tJMHVVW-U6VCyYcxAjadE96gxpJVA4Q")
    String accessToken,

    @Schema(description = "Refresh token для обновления access token", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzUzNjIwMTExLCJleHAiOjE3NTEwMjgxMTEsInRva2VuX3R5cGUiOiJyZWZyZXNoIn0.byxW6y-zHyJJsmdloPv7w9PeJuTCAiLHUBZDGcgDb4x0DfOtGRy4IPTwc7QOBYcLEEcfvR9q3T9efSt7n-sn6A")
    String refreshToken,

    @Schema(description = "Время жизни access token в секундах", example = "480000")
    Long expiresIn
){}
