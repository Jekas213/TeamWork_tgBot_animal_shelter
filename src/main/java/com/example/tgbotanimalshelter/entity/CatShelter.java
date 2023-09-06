package com.example.tgbotanimalshelter.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "cat_shelter")
@NoArgsConstructor
public class CatShelter extends Shelter {
    public CatShelter(Long id, String name, LocalDate dateBirthday, String description, Boolean taken) {
        super(id, name, dateBirthday, description, taken);
    }
}

