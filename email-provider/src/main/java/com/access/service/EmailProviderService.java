package com.access.service;

import java.util.List;

public interface EmailProviderService {
    List<String> getServicesEmails(List<String> serviceNames);
}
