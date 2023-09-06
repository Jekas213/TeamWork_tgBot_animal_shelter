package com.example.tgbotanimalshelter.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dog_parent")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DogParent {
    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    @JoinColumn(name = "dog_id", referencedColumnName = "id")
    private Dog dog;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DogParent dogParent = (DogParent) o;
        return Objects.equals(chatId, dogParent.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId);
    }
}
