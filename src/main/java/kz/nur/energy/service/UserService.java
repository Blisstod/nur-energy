package kz.nur.energy.service;

import kz.nur.energy.dto.LoginUserRequest;
import kz.nur.energy.dto.RegisterUserRequest;
import kz.nur.energy.dto.TokenResponse;
import kz.nur.energy.entity.User;
import kz.nur.energy.repository.UserRepository;
import kz.nur.energy.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    public TokenResponse registerUser(RegisterUserRequest registerUserRequest) {
        Optional<User> existingUser = userRepository.findByMobileNum(registerUserRequest.getMobileNum());
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (registerUserRequest.getFirstName() != null) user.setFirstName(registerUserRequest.getFirstName());
            if (registerUserRequest.getLastName() != null) user.setLastName(registerUserRequest.getLastName());
            userRepository.save(user);

            return new TokenResponse(jwtUtils.generateToken(user.getUsername()));
        }

        User newUser = new User();
        newUser.setMobileNum(registerUserRequest.getMobileNum());
        newUser.setUsername(registerUserRequest.getMobileNum());
        newUser.setFirstName(registerUserRequest.getFirstName());
        newUser.setLastName(registerUserRequest.getLastName());
        newUser.setEmail(registerUserRequest.getEmail());
        newUser.setUserType(registerUserRequest.getUserType());
        newUser.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        userRepository.save(newUser);

        return new TokenResponse(jwtUtils.generateToken(newUser.getUsername()));
    }

    public TokenResponse login(LoginUserRequest loginUserRequest) {
        String mobileNum = loginUserRequest.getMobileNum();
        String password = loginUserRequest.getPassword();

        User user = userRepository.findByMobileNum(mobileNum)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Неверный пароль");
        }

        return new TokenResponse(jwtUtils.generateToken(user.getUsername()));
    }

//    private String normalizePhoneNumber(String phoneNumber) {
//        return phoneNumber.replaceAll("[^0-9]", "");
//    }
}

