package com.example.schedulejpa.entity;


import com.example.schedulejpa.exception.CustomBadRequestException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Setter
    private String username;

    private String email;

    @Setter
    private String password;

    @Transient
    private LocalDateTime updatedAt;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public static void isMine(Long id, Long loginId, String warning) {
        if (!Objects.equals(id, loginId)) {
            throw new CustomBadRequestException(warning);
        }
    }
}
