package kz.nur.energy.dto;

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
public class OrderResponse {
    private UUID id;
    private Date createdDate;
    private OrderStatus status;
    private String phoneNumber;
    private BigDecimal price;
    private Double distance;
    private String startAddress;
    private String destinationAddress;
    private LocalDateTime pickUpTime;

    //TODO: add auto, user, driver

    private ControlPointResponse startPoint;
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
