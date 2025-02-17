package com.example.simplesns.domain.friend.entity;

import jakarta.persistence.AttributeConverter;

public class FriendStatusConverter implements AttributeConverter<FriendStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(FriendStatus friendStatus) {
        return friendStatus.getCode();
    }

    @Override
    public FriendStatus convertToEntityAttribute(Integer code) {
        return FriendStatus.of(code);
    }
}
