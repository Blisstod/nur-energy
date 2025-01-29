package kz.nur.energy.dto;

import kz.nur.energy.entity.ControlPoint;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ControlPointResponse {
    private UUID id;
    private String address;
    private String longitude;
    private String latitude;

    public static ControlPointResponse of(final ControlPoint point) {
        if (point==null){
            return ControlPointResponse.builder().build();
        }
        return ControlPointResponse.builder()
                .id(point.getId())
                .address(point.getAddress())
                .longitude(String.valueOf(point.getLongitude()))
                .latitude(String.valueOf(point.getLatitude()))
                .build();
    }
}
