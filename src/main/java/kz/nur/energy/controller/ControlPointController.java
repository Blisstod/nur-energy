package kz.nur.energy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.nur.energy.dto.ControlPointResponse;
import kz.nur.energy.service.ControlPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "API для работы с маршрутами")
@RequestMapping(value = "/rest/taxi/point")
public class ControlPointController {

    @Autowired
    private ControlPointService controlPointService;

    @Operation(summary = "Получить список точек", description = "Возвращает информацию о точках")
    @GetMapping(value = "", name = "Получение точек", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ControlPointResponse>> getAll() {
        return ResponseEntity.ok(controlPointService.getAll());
    }

    @Operation(summary = "Получить точку", description = "Возвращает информацию о точке по его ID")
    @GetMapping(value = "/{id}", name = "Получение точки", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ControlPointResponse> getById(
            @Parameter(description = "ID точки") @PathVariable UUID id) {
        return ResponseEntity.ok(controlPointService.getById(id));
    }
}
