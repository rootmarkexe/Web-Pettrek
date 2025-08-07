package com.pettrek.backend.passport.models;

import com.pettrek.backend.auth.models.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@DynamicUpdate
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

    @Column(name = "Номер паспорта", unique = true)
    private Integer passportNumber;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
}
