package kz.nur.energy.service;

import kz.nur.energy.dto.RegisterUserRequest;
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

    public String registerUser(RegisterUserRequest registerUserRequest) {
        String normalizedPhone = normalizePhoneNumber(registerUserRequest.getMobileNum());

        Optional<User> existingUser = userRepository.findByMobileNum(normalizedPhone);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (registerUserRequest.getFirstName() != null) user.setFirstName(registerUserRequest.getFirstName());
            if (registerUserRequest.getLastName() != null) user.setLastName(registerUserRequest.getLastName());
            userRepository.save(user);

            return jwtUtils.generateToken(user.getUsername());
        }

        User newUser = new User();
        newUser.setMobileNum(normalizedPhone);
        newUser.setUsername(registerUserRequest.getMobileNum());
        newUser.setFirstName(registerUserRequest.getFirstName());
        newUser.setLastName(registerUserRequest.getLastName());
        newUser.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        userRepository.save(newUser);

        return jwtUtils.generateToken(newUser.getUsername());
    }

    public String login(String mobileNum, String password) {
        String normalizedPhone = normalizePhoneNumber(mobileNum);

        User user = userRepository.findByMobileNum(normalizedPhone)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Неверный пароль");
        }

        return jwtUtils.generateToken(user.getUsername());
    }

    private String normalizePhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("[^0-9]", "");
    }
}

