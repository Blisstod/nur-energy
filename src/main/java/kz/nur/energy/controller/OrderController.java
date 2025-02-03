package kz.nur.energy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "API для работы с заказами")
@RequestMapping(value = "/rest/taxi/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Создать заказ", description = "Создает новый заказ и уведомляет водителей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказ успешно создан",
                    content = @Content(schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    })
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> create(
            @RequestBody @Parameter(description = "Данные заказа") OrderRequest request) {
        var response = orderService.create(request);
        orderService.notifyDrivers(response);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Получить заказ", description = "Возвращает информацию о заказе по его ID")
    @GetMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> getById(
            @Parameter(description = "ID заказа") @PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.getById(orderId));
    }

    @Operation(summary = "Получение активных заказов", description = "Возвращает список всех активных заказов")
    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderResponse>> getActive() {
        return ResponseEntity.ok(orderService.getActiveOrders());
    }

    @Operation(summary = "Отменить заказ", description = "Отменяет заказ по его ID")
    @PostMapping(value = "/cancel/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> cancelOrder(
            @Parameter(description = "ID заказа") @PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }

    @Operation(summary = "Забронировать заказ", description = "Бронирует заказ по его ID")
    @PostMapping(value = "/book/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> bookOrder(
            @Parameter(description = "ID заказа") @PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.bookOrder(orderId));
    }

    @Operation(summary = "Получить вакантные заказы для водителя", description = "Выдает вакантные заказы для водителя, чтобы те могли принять")
    @GetMapping(value = "/vacant", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderResponse>> getVacant() {
        return ResponseEntity.ok(orderService.getVacantOrders());
    }

    @Operation(summary = "Получить заказы которые создал пассажир", description = "Выдает те заказы которые создал сам пассажир")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderResponse>> getAll() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @Operation(summary = "Закончить заказ", description = "Закончить заказ")
    @PostMapping(value = "/finish/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> finish(
            @Parameter(description = "ID заказа") @PathVariable UUID orderId
    ){
        return ResponseEntity.ok(orderService.finishOrder(orderId));
    }
}