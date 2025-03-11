package com.weather.tracker.utils;

public class AirQualityUtils {

    public static int calculateAQI(double so2, double no2, double pm10, double pm25, double o3, double co) {
        // Determine the highest AQI based on pollutant concentrations
        int aqiSO2 = getAQIForSO2(so2);
        int aqiNO2 = getAQIForNO2(no2);
        int aqiPM10 = getAQIForPM10(pm10);
        int aqiPM25 = getAQIForPM25(pm25);
        int aqiO3 = getAQIForO3(o3);
        int aqiCO = getAQIForCO(co);

        // Return the highest AQI
        return Math.max(Math.max(Math.max(Math.max(Math.max(aqiSO2, aqiNO2), aqiPM10), aqiPM25), aqiO3), aqiCO);
    }

    public static String getQualitativeName(int aqi) {
        switch (aqi) {
            case 1:
                return "Good";
            case 2:
                return "Fair";
            case 3:
                return "Moderate";
            case 4:
                return "Poor";
            case 5:
                return "Very Poor";
            default:
                return "Unknown";
        }
    }

    private static int getAQIForSO2(double so2) {
        if (so2 < 20) return 1;
        else if (so2 < 80) return 2;
        else if (so2 < 250) return 3;
        else if (so2 < 350) return 4;
        else return 5;
    }

    private static int getAQIForNO2(double no2) {
        if (no2 < 40) return 1;
        else if (no2 < 70) return 2;
        else if (no2 < 150) return 3;
        else if (no2 < 200) return 4;
        else return 5;
    }

    private static int getAQIForPM10(double pm10) {
        if (pm10 < 20) return 1;
        else if (pm10 < 50) return 2;
        else if (pm10 < 100) return 3;
        else if (pm10 < 200) return 4;
        else return 5;
    }

    private static int getAQIForPM25(double pm25) {
        if (pm25 < 10) return 1;
        else if (pm25 < 25) return 2;
        else if (pm25 < 50) return 3;
        else if (pm25 < 75) return 4;
        else return 5;
    }

    private static int getAQIForO3(double o3) {
        if (o3 < 60) return 1;
        else if (o3 < 100) return 2;
        else if (o3 < 140) return 3;
        else if (o3 < 180) return 4;
        else return 5;
    }

    private static int getAQIForCO(double co) {
        if (co < 4400) return 1;
        else if (co < 9400) return 2;
        else if (co < 12400) return 3;
        else if (co < 15400) return 4;
        else return 5;
    }
}
