package com.access.service;

import com.access.dto.AttackAttemptDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupNotifierServiceImpl implements GroupNotifierService{
    final RestTemplate restTemplate;
    @Override
    public List<String> getMails(List<String> services) {
        return List.of();
    }
}
