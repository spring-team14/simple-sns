package com.example.simplesns.domain.friend.entity;

import lombok.Getter;

public enum FriendStatus {
    WAIT(0, "대기"),
    ACCEPT(1, "승인"),
    REJECT(2, "거절");

    @Getter
    private final Integer code;
    private final String description;

    FriendStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public static FriendStatus of(Integer code) {
        for (FriendStatus friendStatus : FriendStatus.values()) {
            if (friendStatus.code.equals(code)) {
                return friendStatus;
            }
        }
        return null;
    }
}
