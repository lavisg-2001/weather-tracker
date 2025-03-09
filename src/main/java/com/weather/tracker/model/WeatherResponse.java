package com.weather.tracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@AllArgsConstructor
//@NoArgsConstructor
public class WeatherResponse {

    @JsonProperty("description")
    private String description;

    @JsonProperty("temperature")
    private double temperature;

    @JsonProperty("humidity")
    private int humidity;

    @JsonProperty("windSpeed")
    private double windSpeed;

    public WeatherResponse(String description, double temperature, int humidity, double windSpeed) {
        this.description = description;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }
}
