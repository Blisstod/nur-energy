package kz.nur.energy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.nur.energy.enums.UserTypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Запрос на логин пользователя")
@Builder
public class LoginUserRequest {

    @Schema(description = "Мобильный номер", example = "+77011234567")
    private String mobileNum;

    @Schema(description = "Пароль")
    private String password;
}
