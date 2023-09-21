package com.example.tgbotanimalshelter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_birthday")
    private LocalDate dateBirthday;

    @Column(name = "description")
    private String description;

    @Column(name = "taken")
    private Boolean taken;
}
