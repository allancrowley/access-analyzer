package com.access.controller;

import com.access.service.EmailProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import static com.access.UrlConstants.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailProviderController {
    final EmailProviderService emailProviderService;

    //TODO extract path into config
    @GetMapping(EMAIL_PATH)
    List<String> getServicesEmails(@RequestParam List<String> services) {
        log.debug("EmailProviderController: getServicesEmails: argument: {}", services);
        List<String> emails = emailProviderService.getServicesEmails(services);
        log.debug("EmailProviderController: getServicesEmails: received from service: {}", emails);
        return emails;
    }
}
