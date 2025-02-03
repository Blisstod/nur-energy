package kz.nur.energy.service;

import kz.nur.energy.dto.ControlPointResponse;
import org.springframework.stereotype.Service;

@Service
public class DistanceService {

    // https://taxi.yandex.kz/ru_kz/astana/tariff/

    private static final double EARTH_RADIUS_KM = 6371.0;
    private static final int MIN_PRICE = 350;
    private static final int PRICE_PER_KM = 120;

    public double calculateDistance(ControlPointResponse startPoint, ControlPointResponse endPoint) {
        double lat1Rad = Math.toRadians(Double.parseDouble(startPoint.getLatitude()));
        double lon1Rad = Math.toRadians(Double.parseDouble(startPoint.getLongitude()));
        double lat2Rad = Math.toRadians(Double.parseDouble(endPoint.getLatitude()));
        double lon2Rad = Math.toRadians(Double.parseDouble(endPoint.getLongitude()));

        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        double a = Math.pow(Math.sin(deltaLat / 2), 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(deltaLon / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        return EARTH_RADIUS_KM * c;
    }

    public int calculatePrice(double distance){
        if (distance <= 1.0){
            return MIN_PRICE;
        }
        return (int)(((distance - 1) * PRICE_PER_KM) + MIN_PRICE);
    }

}
