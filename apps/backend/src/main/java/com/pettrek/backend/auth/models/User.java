package com.pettrek.backend.auth.models;

import com.pettrek.backend.passport.models.Pet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;

@Entity
@Table(name = "auth_users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "Email", unique = true, nullable = false)
    private String email;

    @Column(name = "isEnabled", nullable = false)
    private boolean enabled = false;

    private String verificationCode;

    private String resetPasswordToken;
    @Column(name = "Name",nullable=false)
    private String name;

    @Column(name="secondName", nullable = false)
    private String secondName;

    @Column(name="surname", nullable = true)
    private String surname;

    @Column(name="dateOfBirth", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets;
}
