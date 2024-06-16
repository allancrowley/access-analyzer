package com.access.service;

import com.access.repo.AccessDb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class BlockedIpProviderServiceImpl implements BlockedIpProviderService {
    @Autowired
    AccessDb accessDb;


    @Override
    public List<String> getBlockedList() {
        log.debug("BlockedIpProviderServiceImpl: getBlockedList");

        List<String> ipList = accessDb.getBlockedList();

        log.debug("BlockedIpProviderServiceImpl: getBlockedList: received from DB: {}", ipList);
        return ipList;
    }
}
