package com.access.emailprovider;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class EmailProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailProviderApplication.class, args);
    }

}
