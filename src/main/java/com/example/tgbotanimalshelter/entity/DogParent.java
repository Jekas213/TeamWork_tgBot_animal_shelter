package com.example.tgbotanimalshelter.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "dog_parent")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "dog")
@Getter
@Setter
@ToString
@Builder
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
}
