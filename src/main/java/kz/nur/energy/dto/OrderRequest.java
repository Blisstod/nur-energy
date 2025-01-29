package kz.nur.energy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@Builder
public class OrderRequest {
    private String phoneNumber;
    private BigDecimal price;
    private Double distance;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private String pickUpTime;

    private ControlPointResponse startPoint;
    private ControlPointResponse destinationPoint;
}