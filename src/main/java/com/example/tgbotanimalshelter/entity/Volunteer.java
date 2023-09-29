package com.example.tgbotanimalshelter.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "volunteer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Volunteer {

    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "name")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return Objects.equals(chatId, volunteer.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId);
    }
}
