package kz.nur.energy.dto;

import kz.nur.energy.enums.UserTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String mobileNum;
    private UserTypeEnum userType;
}
