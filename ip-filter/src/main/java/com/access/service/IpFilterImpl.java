package com.access.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class IpFilterImpl implements IpFilterService {
    final ServiceConfiguration serviceConfiguration;
    final WebClient.Builder webClientBuilder;
    private volatile Set<String> ipCache = new HashSet<>();

    @Override
    public boolean checkIp(String ip) {
        log.debug("Received ip: {}", ip);
        return ipCache.contains(ip);
    }

    @Scheduled(fixedRateString = "${ip.cache.update.interval}")
    private void updateCache() {
        emailRequest()
                .doOnError(e -> log.error("Error updating IP cache: {}", e.getMessage()))
                .subscribe(newIpCache -> {
                    if (newIpCache != null) {
                        ipCache = newIpCache;
                        log.info("IP cache updated {}", ipCache);
                    } else {
                        log.warn("Failed to update IP cache. Retrieved IP set is null.");
                    }
                });
    }

    private Mono<Set<String>> emailRequest() {
        String url = getUrl();
        WebClient client = webClientBuilder.build();

        return client.get()
                .uri(url)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {
                })
                .map(ipList -> {
                    Set<String> ipSetResult = new HashSet<>(ipList);
                    log.debug("Fetched IP list: {}", ipSetResult);
                    return ipSetResult;
                })
                .doOnError(e -> log.error("Error fetching IP list from {}: {}", url, e.getMessage()))
                .onErrorResume(e -> Mono.just(new HashSet<>()));
    }

    private String getUrl() {
        return String.format("http://%s:%d/%s",
                serviceConfiguration.getHost(),
                serviceConfiguration.getPort(),
                serviceConfiguration.getPath());
    }
}
