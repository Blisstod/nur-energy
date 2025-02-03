package kz.nur.energy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.nur.energy.entity.ControlPoint;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ControlPointResponse {

    @Schema(description = "Адрес", example = "Медеу")
    private String address;

    @Schema(description = "Долгота", example = "77.053")
    private String longitude;

    @Schema(description = "Широта", example = "42.041")
    private String latitude;

    public static ControlPointResponse of(final ControlPoint point) {
        if (point==null){
            return ControlPointResponse.builder().build();
        }
        return ControlPointResponse.builder()
                .address(point.getAddress())
                .longitude(String.valueOf(point.getLongitude()))
                .latitude(String.valueOf(point.getLatitude()))
                .build();
    }
}
