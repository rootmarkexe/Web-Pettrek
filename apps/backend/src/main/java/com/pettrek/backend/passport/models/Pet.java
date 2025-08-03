package com.pettrek.backend.passport.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "pets")
@Getter
@Setter
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Кличка")
    private String name;

    @Column(name = "Вид")
    private String specie;

    @Column(name = "Пол")
    private String gender;

    @Column(name = "Дата рождения")
    private LocalDate dateOfBirth;

    @Column(name = "Порода")
    private String breed;

    @Column(name = "Шерсть")
    private String hair;

    @Column(name  = "Вес")
    private Double weight;

    @Column(name = "Краткая характеристика")
    private String feature;
}
