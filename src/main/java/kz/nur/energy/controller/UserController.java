package kz.nur.energy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import kz.nur.energy.dto.LoginUserRequest;
import kz.nur.energy.dto.RegisterUserRequest;
import kz.nur.energy.dto.TokenResponse;
import kz.nur.energy.dto.UserInfo;
import kz.nur.energy.entity.User;
import kz.nur.energy.exceptions.UnauthorizedException;
import kz.nur.energy.service.UserService;
import kz.nur.energy.service.auth.CustomUserDetails;
import kz.nur.energy.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityUtils securityUtils;

    @Operation(summary = "Регистрация пользователя", description = "Создает нового пользователя и возвращает токен")
    @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован",
            content = @Content(schema = @Schema(implementation = String.class)))
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponse> registerUser(
            @RequestBody @Validated RegisterUserRequest registerUserRequest) {
        return ResponseEntity.ok(userService.registerUser(registerUserRequest));
    }

    @Operation(summary = "Вход в систему", description = "Аутентифицирует пользователя по номеру телефона и паролю")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfo> login(
            @RequestBody @Validated LoginUserRequest loginUserRequest
    ) {
        return ResponseEntity.ok(userService.login(loginUserRequest));
    }

    @Operation(summary = "Получение информации об авторизованном пользователе", description = "Выдает информацию о пользователе который авторизовался")
    @GetMapping(value = "/visitor/info", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserInfo> getVisitorInfo() {
        User currentUser = SecurityUtils.getCurrentUser();

        if (currentUser == null) {
            throw new UnauthorizedException("Нужна авторизация");
        }

        return ResponseEntity.ok(UserInfo.of(currentUser));
    }
}
