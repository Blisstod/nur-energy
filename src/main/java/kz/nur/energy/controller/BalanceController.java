package kz.nur.energy.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.nur.energy.dto.BalanceInfo;
import kz.nur.energy.entity.User;
import kz.nur.energy.exceptions.UnauthorizedException;
import kz.nur.energy.service.BalanceService;
import kz.nur.energy.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "API для работы с балансами")
@RequestMapping(value = "/rest/balance")
public class BalanceController {

    @Autowired
    BalanceService balanceService;

    @GetMapping(value = "", name = "Получение текущего баланса", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BalanceInfo> getUserInfo() {
        User user = SecurityUtils.getCurrentUser();

        if (user == null) {
            throw new UnauthorizedException("Нужна авторизация");
        }

        return ResponseEntity.ok(balanceService.getBalanceInfo(user));
    }

    @PostMapping(value = "/accrual", name = "Пополнение счета", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> accrualBalance(
            @Parameter(description = "Сумма для пополнения", name = "refill", example = "100", required = true)
            @RequestParam Long refill
    ) {
        User user = SecurityUtils.getCurrentUser();
        if (user == null) {
            throw new UnauthorizedException("Нужна авторизация");
        }

        return ResponseEntity.ok(balanceService.accrualBalance(user, refill));
    }
}
