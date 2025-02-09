package kz.nur.energy.service;

import kz.nur.energy.dto.LoginUserRequest;
import kz.nur.energy.dto.RegisterUserRequest;
import kz.nur.energy.dto.TokenResponse;
import kz.nur.energy.dto.UserInfo;
import kz.nur.energy.entity.Balance;
import kz.nur.energy.entity.User;
import kz.nur.energy.repository.BalanceRepository;
import kz.nur.energy.repository.UserRepository;
import kz.nur.energy.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Transactional
    public TokenResponse registerUser(RegisterUserRequest registerUserRequest) {
        Optional<User> existingUser = userRepository.findByMobileNum(registerUserRequest.getMobileNum());
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (registerUserRequest.getFirstName() != null) user.setFirstName(registerUserRequest.getFirstName());
            if (registerUserRequest.getLastName() != null) user.setLastName(registerUserRequest.getLastName());

            if (user.getBalance() == null) {
                Balance newBalance = new Balance();
                newBalance.setUser(user);
                balanceRepository.save(newBalance);
                user.setBalance(newBalance);
            }

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

        Balance newBalance = new Balance();
        newBalance.setUser(newUser);
        balanceRepository.save(newBalance);

        newUser.setBalance(newBalance);
        userRepository.save(newUser);

        return new TokenResponse(jwtUtils.generateToken(newUser.getUsername()));
    }

    @Transactional
    public UserInfo login(LoginUserRequest loginUserRequest) {
        log.info("Login user request: {}", loginUserRequest.toString());

        String mobileNum = loginUserRequest.getMobileNum();
        String password = loginUserRequest.getPassword();

        User user = userRepository.findByMobileNum(mobileNum)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Неверный пароль");
        }

        if (user.getBalance() == null) {
            Balance newBalance = new Balance();
            newBalance.setUser(user);
            balanceRepository.save(newBalance);
            user.setBalance(newBalance);
            userRepository.save(user);
        }

        String token = jwtUtils.generateToken(user.getUsername());

        return UserInfo.of(user, token);
    }
}

