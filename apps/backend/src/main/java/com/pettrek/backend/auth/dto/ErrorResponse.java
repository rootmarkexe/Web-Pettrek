package com.pettrek.backend.auth.dto;

import java.time.LocalDateTime;

public record ErrorResponse (
    Integer status,
    String message,
    LocalDateTime timestamp
){}
