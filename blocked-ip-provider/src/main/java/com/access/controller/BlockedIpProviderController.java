package com.access.controller;

import com.access.service.BlockedIpProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static com.access.UrlConstants.IP_PATH;


@RestController
@RequiredArgsConstructor
@Slf4j
public class BlockedIpProviderController {

    final BlockedIpProviderService blockedIpProviderService;

    @GetMapping(IP_PATH)
    List<String> getBlockedList() {
        List<String> ipList = blockedIpProviderService.getBlockedList();
        log.debug("BlockedIpProviderController: getBlockedList: received from service: {}", ipList);
        return ipList;
    }


}
