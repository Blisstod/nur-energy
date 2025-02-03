package kz.nur.energy.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.nur.energy.dto.AutoItem;
import kz.nur.energy.dto.BrandItem;
import kz.nur.energy.dto.ModelItem;
import kz.nur.energy.entity.Auto;
import kz.nur.energy.entity.AutoModel;
import kz.nur.energy.entity.User;
import kz.nur.energy.service.AutoService;
import kz.nur.energy.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "API для обработки данных автомобилей")
@RequestMapping(value = "/rest")
public class AutoController {

    @Autowired
    AutoService autoService;

    @PostMapping(value = "/auto", name = "Добавить авто", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AutoItem> addAuto(@Parameter(name = "request", description = "Данные авто")
                                        @RequestBody @Validated AutoItem request
    ) {
        User user = SecurityUtils.getCurrentUser();
        return ResponseEntity.ok(autoService.addAuto(user, request));
    }

    @PostMapping(value = "/auto-model", name = "Добавить модель", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ModelItem> addAutoModel(@Parameter(name = "auto model request", description = "Данные модели авто")
                                                  @RequestBody @Validated ModelItem request
    ) {
        User user = SecurityUtils.getCurrentUser();
        return ResponseEntity.ok(autoService.addAutoModel(user, request));
    }

    @PostMapping(value = "/auto-brand", name = "Добавить бренд авто", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BrandItem> addAutoBrands(@Parameter(name = "auto model request", description = "Данные бренда авто")
                                                  @RequestBody @Validated BrandItem request
    ) {
        User user = SecurityUtils.getCurrentUser();
        return ResponseEntity.ok(autoService.addAutoBrand(user, request));
    }

    @GetMapping(value = "/autos", name = "Получить все авто пользователя", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AutoItem>> getUserAutos() {
        User user = SecurityUtils.getCurrentUser();
        return ResponseEntity.ok(autoService.getAutoList(user));
    }

}
