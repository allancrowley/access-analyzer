package com.access.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class IpFilterImpl implements IpFilterService{
    @Override
    public boolean checkIp(String ip) {
        return false;
    }
}
