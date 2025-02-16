package kz.nur.energy.service;

import kz.nur.energy.dto.ControlPointResponse;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DistanceService {

    private static final Logger logger = LoggerFactory.getLogger(DistanceService.class);
    // https://taxi.yandex.kz/ru_kz/astana/tariff/

    private static final double EARTH_RADIUS_KM = 6371.0;
    private static final int MIN_PRICE = 350;
    private static final int PRICE_PER_KM = 120;

    public double calculateDistance(ControlPointResponse startPoint, ControlPointResponse endPoint) {
        double lat1 = Double.parseDouble(startPoint.getLatitude());
        double lon1 = Double.parseDouble(startPoint.getLongitude());
        double lat2 = Double.parseDouble(endPoint.getLatitude());
        double lon2 = Double.parseDouble(endPoint.getLongitude());

        logger.info("StartPoint: lat={}, lon={}", lat1, lon1);
        logger.info("DestinationPoint: lat={}, lon={}", lat2, lon2);

        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        double a = Math.pow(Math.sin(deltaLat / 2), 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(deltaLon / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));
        double distance = EARTH_RADIUS_KM * c;

        logger.info("Calculated distance: {} km", distance);
        return distance;
    }

    public int calculatePrice(double distance){
        if (distance <= 1.0){
            return MIN_PRICE;
        }
        return (int)(((distance - 1) * PRICE_PER_KM) + MIN_PRICE);
    }

}
