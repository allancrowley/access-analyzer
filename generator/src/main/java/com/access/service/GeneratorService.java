package com.access.service;

import org.springframework.http.ResponseEntity;

public interface GeneratorService {
    ResponseEntity<String> getToken();

}
