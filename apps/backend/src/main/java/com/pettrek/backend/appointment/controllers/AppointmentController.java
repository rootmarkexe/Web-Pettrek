package com.pettrek.backend.appointment.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Mapping
public class AppointmentController {

    private AppointmentService appointmentService;

    @PostMapping
}
