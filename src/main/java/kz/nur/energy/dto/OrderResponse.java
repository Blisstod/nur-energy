package kz.nur.energy.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import kz.nur.energy.entity.ControlPoint;
import kz.nur.energy.entity.Order;
import kz.nur.energy.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Data
@Getter
@Setter
@Builder
@Schema(description = "Ответ на запрос заказа")
public class OrderResponse {
    @Schema(description = "Идентификатор заказа", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Дата создания заказа")
    private Date createdDate;

    @Schema(description = "Статус заказа")
    private OrderStatus status;

    @Schema(description = "Номер телефона клиента", example = "+77011234567")
    private String phoneNumber;

    @Schema(description = "Цена поездки", example = "1500.00")
    private BigDecimal price;

    @Schema(description = "Расстояние поездки в км", example = "12.5")
    private Double distance;

    @Schema(description = "Адрес отправления")
    private String startAddress;

    @Schema(description = "Адрес назначения")
    private String destinationAddress;

    @Schema(description = "Время подачи")
    private LocalDateTime pickUpTime;

    //TODO: add auto, user, driver

    @Parameter(description = "Адрес откуда", name = "startPoint", required = true)
    private ControlPointResponse startPoint;

    @Parameter(description = "Адрес куда", name = "destinationPoint", required = true)
    private ControlPointResponse destinationPoint;

    public static OrderResponse of(final Order order) {

        var response =  OrderResponse.builder()
                .id(order.getId())
                .createdDate(order.getCreatedDate())
                .phoneNumber(order.getPhoneNumber())
                .status(order.getStatus())
                .price(order.getPrice())
                .distance(order.getDistance())
                .startAddress(order.getStartAddress())
                .destinationAddress(order.getDestinationAddress())
                .build();

        if (order.getStartPoint() == null){
            response.setStartPoint(ControlPointResponse.builder()
                    .address(order.getStartAddress()).build());
        } else {
            response.setStartPoint(ControlPointResponse.of(order.getStartPoint()));
        }

        if (order.getDestinationPoint() == null){
            response.setDestinationPoint(ControlPointResponse.builder()
                    .address(order.getDestinationAddress()).build());
        } else{
            response.setDestinationPoint(ControlPointResponse.of(order.getDestinationPoint()));
        }

//        if (order.getDriver() != null){
//            response.setDriver(UserInfo.of(order.getDriver()));
//        }

        response.setPickUpTime(order.getPickUpTime());
        return response;
    }
}
