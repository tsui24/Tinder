package com.tinder.tinder.utils;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class OSMService {
    private final WebClient osmClient;

    public OSMService(@Qualifier("osmWebClient") WebClient osmClient) {
        this.osmClient = osmClient;
    }
    public String getLocationName(String lat, String lon) {
        try {
            Map<String, Object> response = osmClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/reverse")
                            .queryParam("lat", lat)
                            .queryParam("lon", lon)
                            .queryParam("format", "json")
                            .build())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            return (response != null && response.containsKey("display_name"))
                    ? (String) response.get("display_name")
                    : "Không xác định";
        } catch (Exception e) {
            System.err.println("Lỗi khi gọi OSM API: " + e.getMessage());
            return "Không xác định";
        }
    }

}
