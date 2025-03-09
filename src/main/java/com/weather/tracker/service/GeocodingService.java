package com.weather.tracker.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class GeocodingService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public double[] getCoordinates(String city) throws IOException {
        String GEOCODING_URL = "https://nominatim.openstreetmap.org/search?q=%s&format=json";
        String url = String.format(GEOCODING_URL, city);
        String response = restTemplate.getForObject(url, String.class);

        // Parse the JSON response
        JsonNode root = objectMapper.readTree(response);

        // Check if results are available
        if (root.isArray() && !root.isEmpty()) {
            double lat = root.get(0).get("lat").asDouble();
            double lon = root.get(0).get("lon").asDouble();
            return new double[]{lat, lon};
        } else {
            throw new IOException("No results found for the specified city.");
        }
    }
}
