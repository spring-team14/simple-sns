package com.example.simplesns.domain.friend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Friend {
    @Id
    private Long id;

}
