package com.example.tgbotanimalshelter.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "dog_shelter")
@NoArgsConstructor
public class DogShelter extends Shelter {
    public DogShelter(Long id, String name, LocalDate dateBirthday, String description, Boolean isTaken) {
        super(id, name, dateBirthday, description, isTaken);
    }
}
