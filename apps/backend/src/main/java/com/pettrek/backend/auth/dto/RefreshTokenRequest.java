package com.pettrek.backend.auth.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(

    @NotBlank
    @Schema(description = "Refresh token для обновления access token", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzUzNjIwMTExLCJleHAiOjE3NTEwMjgxMTEsInRva2VuX3R5cGUiOiJyZWZyZXNoIn0.byxW6y-zHyJJsmdloPv7w9PeJuTCAiLHUBZDGcgDb4x0DfOtGRy4IPTwc7QOBYcLEEcfvR9q3T9efSt7n-sn6A")
    String refreshToken
){}
