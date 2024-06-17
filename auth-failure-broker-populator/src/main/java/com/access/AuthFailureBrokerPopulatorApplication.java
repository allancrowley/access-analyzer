package com.access;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.*;
import static com.access.UrlConstants.*;

@SpringBootApplication
@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthFailureBrokerPopulatorApplication {
	private final StreamBridge streamBridge;

	@Value("${app.analyzer.producer.binding.name}")
	private String producerBindingName;


	public static void main(String[] args) {
		SpringApplication.run(AuthFailureBrokerPopulatorApplication.class, args);
	}

	@PostMapping(AUTH_FAILURE_ROUTING_PATH)
	public void routeAuthFailureDto(@RequestBody String authFailureDto) {
		log.trace("AuthFailureBrokerPopulatorController: routeAuthFailureDto: received {}", authFailureDto);
		streamBridge.send(producerBindingName, authFailureDto);
		log.debug("AuthFailureBrokerPopulatorController: routeAuthFailureDto: routed to message broker {} with binding name {}",
				authFailureDto, producerBindingName);
	}

}
