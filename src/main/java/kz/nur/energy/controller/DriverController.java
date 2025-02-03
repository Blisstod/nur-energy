package kz.nur.energy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.nur.energy.dto.OrderResponse;
import kz.nur.energy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "API для работы с заказами водителя")
@RequestMapping(value = "/rest/taxi/driver/order")
public class DriverController {
    @Autowired
    private OrderService orderService;

    @Operation(summary = "Заказы которые принял водитель", description = "Выдает заказы которые принял водитель")
    @GetMapping(value = "/booked", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderResponse>> getBooked() {
        return ResponseEntity.ok(orderService.getBookedOrders());
    }

    @Operation(summary = "Заказы которые водитель уже завершил", description = "Выдает заказы которые водитель уже завершил")
    @GetMapping(value = "/finished", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderResponse>> getFinished() {
        return ResponseEntity.ok(orderService.getFinishedOrders());
    }
}
