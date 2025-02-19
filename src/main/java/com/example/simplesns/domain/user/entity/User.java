package com.example.simplesns.domain.user.entity;

import com.example.simplesns.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthdate;

    private String image;
    private String mbti;

    @Column(nullable = false)
    private String password;

    private LocalDateTime deletedAt;

    public User(String email, String name, LocalDate birthdate, String image, String mbti, String password) {
        this.email = email;
        this.name = name;
        this.birthdate = birthdate;
        this.image = image;
        this.mbti = mbti;
        this.password = password;
    }

    public User(String email, String name, LocalDate birthdate, String password) {
        this.email = email;
        this.name = name;
        this.birthdate = birthdate;
        this.password = password;
    }

    public void updateProfile(String name, LocalDate birthdate, String image, String mbti) {
        this.name = name;
        this.birthdate = birthdate;
        this.image = image;
        this.mbti = mbti;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

}