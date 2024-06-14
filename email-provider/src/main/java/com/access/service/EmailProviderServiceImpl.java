package com.access.service;

import com.access.repo.ServiceRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailProviderServiceImpl implements EmailProviderService {
    final ServiceRepo serviceRepo;

    @Override
    public List<String> getServicesEmails(List<String> serviceNames) {
        log.debug("EmailProviderServiceImpl: getServicesEmails: argument: {}", serviceNames);
        List<String> emails = serviceRepo.findEmailsByServiceNameIn(serviceNames);
        log.debug("EmailProviderServiceImpl: getServicesEmails: received from DB: {}", emails);
        return emails;
    }
}