package com.access.model;


import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.TreeMap;

@RedisHash
@RequiredArgsConstructor
@Getter
public class FailureList {
    @Id
    @NonNull
    String subnet;
    TreeMap<Long, String> attempt;
}
