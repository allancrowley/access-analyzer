package com.access.controller;

import com.access.service.GeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/simulate")
@Slf4j
public class SimulationController {
    private final GeneratorService generatorService;

   @PostMapping("/send")
    public ResponseEntity<String> runSimulation() {
        return generatorService.getToken();
    }
}
