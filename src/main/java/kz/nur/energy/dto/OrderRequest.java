package kz.nur.energy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос на создание заказа")
public class OrderRequest {
    @Schema(description = "Номер телефона клиента", example = "+77011234567")
    private String phoneNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Schema(description = "Время подачи", example = "2025-02-01 14:30:00.000")
    private String pickUpTime;

    private ControlPointResponse startPoint;
    private ControlPointResponse destinationPoint;
}