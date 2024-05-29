package com.access.model;

import lombok.Getter;
import lombok.NonNull;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.NavigableMap;
import java.util.TreeMap;

@RedisHash
@RequiredArgsConstructor
@Getter
public class FailureList {
    @Id
    @NonNull
    String subnet;
    @NonNull
    TreeMap<Long, String> attemptsMap;
}