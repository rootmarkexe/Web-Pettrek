package com.pettrek.backend.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class UserProfile {

    @Column(name = "Name",nullable=false)
    private String name;

    @Column(name="secondName", nullable = false)
    private String secondName;

    @Column(name="surname", nullable = true)
    private String surname;

    @Column(name="dateOfBirth", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Column(name="city", nullable = false)
    private String city;

}
