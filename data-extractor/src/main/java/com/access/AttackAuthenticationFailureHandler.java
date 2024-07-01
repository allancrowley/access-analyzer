package com.access;

import com.access.dto.AuthFailureDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Objects;


@RequiredArgsConstructor
public class AttackAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final WebClient.Builder webClientBuilder;

    @Value("${AUTH_FAILURE_BROKER_POPULATOR_HOST}")
    private String authFailureBrokerPopulatorHost;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String serviceName = getServiceName(request);
        String clientSubnet = getSubnet(request);
        Long timestamp = System.currentTimeMillis();
        AuthFailureDto authFailureDto = new AuthFailureDto(clientSubnet, timestamp, serviceName);
        sendAuthFailureDto(authFailureDto);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Authentication failed from subnet: " + exception.getMessage());
    }

    private String getServiceName(HttpServletRequest request) {
        String xFHost = request.getHeader("X-Forwarded-Host");
        return !Objects.isNull(xFHost) ? xFHost.split(":")[0] : request.getRemoteHost();
    }

    private String getSubnet(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        String ip = !Objects.isNull(xfHeader) ? xfHeader.split(",")[0].trim() : request.getRemoteAddr();
        String[] octets = ip.split("\\.");
        return octets[0] + "." + octets[1] + "." + octets[2];
    }

    private void sendAuthFailureDto(AuthFailureDto authFailureDto) {
        WebClient client = webClientBuilder.build();
        client.post()
                .uri(authFailureBrokerPopulatorHost)
                .body(BodyInserters.fromValue(authFailureDto))
                .retrieve()
                .bodyToMono(Void.class)// not waiting to response
                .subscribe(); // sub to response
    }
}