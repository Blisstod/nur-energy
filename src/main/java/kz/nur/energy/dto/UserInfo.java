package kz.nur.energy.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import kz.nur.energy.entity.User;
import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@Schema(description = "Инфо о пользователе")
@NoArgsConstructor
public class UserInfo {
    @Schema(description = "UUID")
    private String id;

    @Schema(description = "Имя", example = "John")
    private String firstName;

    @Schema(description = "Фамилия", example = "Doe")
    private String lastName;

    @Schema(description = "Мобильный номер", example = "+77011234567")
    private String phoneNumber;

    @Schema(description = "Email", example = "johndoe@example.com")
    private String email;

    @Schema(description = "Тип пользователя")
    private String userType;

    @Schema(description = "Токен")
    private String token;

    public static UserInfo of(User user, String token){
        return UserInfo.builder()
                .id(String.valueOf(user.getId()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getMobileNum())
                .email(user.getEmail())
                .userType(user.getUserType().getId())
                .token(token)
                .build();
    }

    public static UserInfo of(User user){
        return UserInfo.builder()
                .id(String.valueOf(user.getId()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getMobileNum())
                .email(user.getEmail())
                .userType(user.getUserType().getId())
                .build();
    }
}


