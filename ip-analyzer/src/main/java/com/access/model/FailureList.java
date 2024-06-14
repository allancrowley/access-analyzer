package com.access.model;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.TreeMap;

@RedisHash
@RequiredArgsConstructor
@Getter // delete
@Builder // add builder
@Data // add Data (getters, setters, equal, toString, hashCode)
public class FailureList {
    @Id
    @NonNull
    String subnet;
    @NonNull
    TreeMap<Long, String> attemptsMap;
}