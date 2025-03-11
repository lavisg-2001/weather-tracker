package com.weather.tracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AirQualityResponse {

    @JsonProperty("aqi")
    private int aqi;

    @JsonProperty("qualitativeName")
    private String qualitativeName;

    @JsonProperty("pm25")
    private double pm25;

    @JsonProperty("pm10")
    private double pm10;

    @JsonProperty("co")
    private double co;

    @JsonProperty("no2")
    private double no2;

    @JsonProperty("so2")
    private double so2;

    @JsonProperty("o3")
    private double o3;

    public AirQualityResponse(int aqi, String qualitativeName, double pm25, double pm10, double co, double no2, double so2, double o3){
        this.aqi = aqi;
        this.qualitativeName = qualitativeName;
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.co = co;
        this.no2 = no2;
        this.so2 = so2;
        this.o3 = o3;
    }
}
