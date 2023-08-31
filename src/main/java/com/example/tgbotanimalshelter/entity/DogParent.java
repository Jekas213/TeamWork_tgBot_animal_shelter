package com.example.tgbotanimalshelter.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "dog_parent")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DogParent implements Serializable {
    @Id
    @OneToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private UserChat userChat;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private String status;

    @OneToOne
    @JoinColumn(name = "dog_id", referencedColumnName = "id")
    private DogShelter dog;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DogParent dogParent = (DogParent) o;
        return Objects.equals(userChat, dogParent.userChat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userChat);
    }
}
