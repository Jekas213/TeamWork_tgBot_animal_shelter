package com.example.tgbotanimalshelter.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_chat")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserChat {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "username")
    private String username;
    @Column(name = "volunteer_id")
    private Long volunteerId;
    @Column(name = "report_id")
    private Long reportId;
    @Column(name = "status_user_chat")
    @Enumerated(EnumType.STRING)
    private StatusUserChat statusUserChat;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserChat userChat = (UserChat) o;
        return Objects.equals(id, userChat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
