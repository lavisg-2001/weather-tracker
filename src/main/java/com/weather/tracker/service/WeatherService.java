package com.weather.tracker.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.tracker.model.AirQualityResponse;
import com.weather.tracker.model.WeatherResponse;
import com.weather.tracker.utils.AirQualityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

@Service
public class WeatherService {

    @Value("${openweathermap.api.key}")
    private String apiKey;

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

        // Parse pollutant concentrations
        JsonNode components = root.path("list").get(0).path("components");
        double so2 = components.path("so2").asDouble();
        double no2 = components.path("no2").asDouble();
        double pm10 = components.path("pm10").asDouble();
        double pm25 = components.path("pm2_5").asDouble();
        double o3 = components.path("o3").asDouble();
        double co = components.path("co").asDouble();

        // Calculate AQI and qualitative name
        int aqi = AirQualityUtils.calculateAQI(so2, no2, pm10, pm25, o3, co);
        String qualitativeName = AirQualityUtils.getQualitativeName(aqi);

        // Return the enhanced response
        return new AirQualityResponse(aqi, qualitativeName, pm25, pm10, co, no2, so2, o3);
    }

    private WeatherResponse parseWeatherResponse(JsonNode root) {
        return new WeatherResponse(
                root.get("weather").get(0).get("description").asText(),
                root.get("main").get("temp").asDouble() - 273.15,
                root.get("main").get("humidity").asInt(),
                root.get("wind").get("speed").asDouble()
        );
    }

}
