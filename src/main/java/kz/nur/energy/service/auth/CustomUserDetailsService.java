package kz.nur.energy.service.auth;

import kz.nur.energy.entity.User;
import kz.nur.energy.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByMobileNum(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не найден: " + username);
        }

        return new CustomUserDetails(userOptional.get());
    }
}
