package com.weather.tracker.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.tracker.model.AirQualityResponse;
import com.weather.tracker.model.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

@Service
public class WeatherService {

    @Value("${openweathermap.api.key}")
    private String apiKey;

//    @Value("${geocoding.api.key}")
//    private String goeApiKey;

//    private final String API_KEY = "your_openweathermap_api_key";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public double[] getCoordinates(String city) throws IOException {
        String GEOCODING_URL = "https://api.opencagedata.com/geocode/v1/json?q=%s&key=%s";
        String url = String.format(GEOCODING_URL, city, apiKey);
        String response = restTemplate.getForObject(url, String.class);
        JsonNode root = objectMapper.readTree(response);

        // Extract latitude and longitude
        double lat = root.get("results").get(0).get("geometry").get("lat").asDouble();
        double lon = root.get("results").get(0).get("geometry").get("lng").asDouble();

        return new double[]{lat, lon};
    }

    public WeatherResponse getWeather(String city) throws IOException {
        String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";
        String url = String.format(WEATHER_URL, city, apiKey);
        String response = restTemplate.getForObject(url, String.class);
        JsonNode root = objectMapper.readTree(response);
        return parseWeatherResponse(root);
    }

    public AirQualityResponse getAirQuality(double lat, double lon) throws IOException {
        String AIR_QUALITY_URL = "https://api.openweathermap.org/data/2.5/air_pollution?lat=%s&lon=%s&appid=%s";
        String url = String.format(AIR_QUALITY_URL, lat, lon, apiKey);
        String response = restTemplate.getForObject(url, String.class);
        JsonNode root = objectMapper.readTree(response);
        return parseAirQualityResponse(root);
    }

    private WeatherResponse parseWeatherResponse(JsonNode root) {
        return new WeatherResponse(
                root.get("weather").get(0).get("description").asText(),
                root.get("main").get("temp").asDouble() - 273.15,
                root.get("main").get("humidity").asInt(),
                root.get("wind").get("speed").asDouble()
        );
    }

    private AirQualityResponse parseAirQualityResponse(JsonNode root) {
        JsonNode components = root.get("list").get(0).get("components");
        return new AirQualityResponse(
                root.get("list").get(0).get("main").get("aqi").asInt(),
                components.get("pm2_5").asDouble(),
                components.get("pm10").asDouble(),
                components.get("co").asDouble(),
                components.get("no2").asDouble(),
                components.get("so2").asDouble(),
                components.get("o3").asDouble()
        );
    }
}
