package kz.nur.energy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.nur.energy.enums.UserTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Запрос на регистрацию пользователя")
public class RegisterUserRequest {

    @Schema(description = "Имя пользователя", example = "+77011234567")
    private String username;

    @Schema(description = "Имя", example = "John")
    private String firstName;

    @Schema(description = "Фамилия", example = "Doe")
    private String lastName;

    @Schema(description = "Пароль")
    private String password;

    @Schema(description = "Email", example = "johndoe@example.com")
    private String email;

    @Schema(description = "Мобильный номер", example = "+77011234567")
    private String mobileNum;

    @Schema(description = "Тип пользователя")
    private UserTypeEnum userType;
}
