package com.weather.tracker.controller;

import com.weather.tracker.model.AirQualityResponse;
import com.weather.tracker.model.WeatherResponse;
import com.weather.tracker.service.GeocodingService;
import com.weather.tracker.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class TrackerController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private GeocodingService geocodingService;

    @RequestMapping(value = "/weather/{city}", method = RequestMethod.GET)
    public ResponseEntity<WeatherResponse> getWeather(@PathVariable String city) {
        try {
            WeatherResponse weatherResponse = weatherService.getWeather(city);
            return ResponseEntity.ok(weatherResponse);
        } catch (HttpClientErrorException e) {
            // Handle specific HTTP errors
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(null); // You can return a custom error message if needed
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch weather data", e);
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch weather data", e);
        }
    }

    @RequestMapping(value = "/air-quality/{lat}/{lon}", method = RequestMethod.GET)
    public ResponseEntity<AirQualityResponse> getAirQuality(@PathVariable double lat, @PathVariable double lon) {
        try {
            AirQualityResponse airQualityResponse = weatherService.getAirQuality(lat, lon);
            return ResponseEntity.ok(airQualityResponse);
        } catch (HttpClientErrorException e) {
            // Handle specific HTTP errors
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(null); // You can return a custom error message if needed
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch air quality data", e);
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch air quality data", e);
        }
    }

    @RequestMapping(value = "/air-quality/{city}", method = RequestMethod.GET)
    public ResponseEntity<AirQualityResponse> getAirQualityByCity(@PathVariable String city) {
        try {
            double[] coordinates = geocodingService.getCoordinates(city);
            double lat = coordinates[0];
            double lon = coordinates[1];
            AirQualityResponse airQualityResponse = weatherService.getAirQuality(lat, lon);
            return ResponseEntity.ok(airQualityResponse);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch air quality data", e);
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch air quality data", e);
        }
    }
}