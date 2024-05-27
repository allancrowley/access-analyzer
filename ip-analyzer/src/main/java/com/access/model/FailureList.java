package com.access.model;

import lombok.Getter;
import lombok.NonNull;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.TreeMap;

@RedisHash
@Getter
public class FailureList {
    @Id
    @NonNull
    String subnet;
    TreeMap<Long, String> attemptsMap;

    public FailureList(@NotNull String subnet, TreeMap<Long, String> attemptsMap) {
        this.subnet = subnet;
        this.attemptsMap = new TreeMap<>();
    }

    public void addAttempt(Long timestamp, String serviceName) {
        attemptsMap.put(timestamp, serviceName);
    }
}
