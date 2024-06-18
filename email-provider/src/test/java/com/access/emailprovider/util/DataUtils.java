package com.access.emailprovider.util;

import com.access.model.EmailEntity;

import java.util.Arrays;
import java.util.List;

public class DataUtils {
    public static List<String> getEmailsList() {
        return Arrays.asList("service1@example.com", "service2@example.com", "service3@example.com", "service4@example.com", "service5@example.com");
    }

    public static List<String> getServiceNamesList() {
        return Arrays.asList("Service1", "Service2", "Service3", "Service4", "Service5");
    }

    public static List<EmailEntity> getEmailEntitiesList() {
        return Arrays.asList(
                new EmailEntity("Service1", "service1@example.com"),
                new EmailEntity("Service2", "service2@example.com"),
                new EmailEntity("Service3", "service3@example.com"),
                new EmailEntity("Service4", "service4@example.com"),
                new EmailEntity("Service5", "service5@example.com")
        );
    }
}
