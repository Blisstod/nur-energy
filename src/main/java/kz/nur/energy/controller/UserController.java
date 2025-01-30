package kz.nur.energy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import kz.nur.energy.dto.RegisterUserRequest;
import kz.nur.energy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Регистрация пользователя", description = "Создает нового пользователя и возвращает токен")
    @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован",
            content = @Content(schema = @Schema(implementation = String.class)))
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(
            @RequestBody @Validated RegisterUserRequest registerUserRequest) {
        String token = userService.registerUser(registerUserRequest);
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Вход в систему", description = "Аутентифицирует пользователя по номеру телефона и паролю")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(
            @RequestParam String mobileNum,
            @RequestParam String password) {
        String token = userService.login(mobileNum, password);
        return ResponseEntity.ok(token);
    }
}
