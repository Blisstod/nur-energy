package kz.nur.energy.controller;

import kz.nur.energy.dto.OrderRequest;
import kz.nur.energy.dto.OrderResponse;
import kz.nur.energy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/rest/taxi/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "", name = "Создать заказ", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> create(@RequestBody OrderRequest request) {
        var response = orderService.create(request);
        orderService.notifyDrivers(response);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{orderId}", name = "Получить заказ", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> getById(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.getById(orderId));
    }

    @GetMapping(value = "/active", name = "Получение активных заказов", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderResponse>> getActive() {
        return ResponseEntity.ok(orderService.getActiveOrders());
    }

    @PostMapping(value = "/cancel/{orderId}", name = "Отменить заказ", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> cancelOrder(
            @PathVariable UUID orderId,
            @RequestParam(required = false) String comment) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId, comment));
    }

    @PostMapping(value = "/book/{orderId}", name = "Забронировать заказ", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> bookOrder(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.bookOrder(orderId));
    }
}