package com.example.tgbotanimalshelter.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "cat_parent")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CatParent implements Serializable {
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
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    @JoinColumn(name = "cat_id", referencedColumnName = "id")
    private CatShelter cat;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatParent catParent = (CatParent) o;
        return Objects.equals(userChat, catParent.userChat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userChat);
    }
}
