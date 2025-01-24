package kz.nur.energy.service.auth;

import kz.nur.energy.entity.User;
import kz.nur.energy.enums.UserTypeEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Преобразуем роль пользователя (UserTypeEnum) в GrantedAuthority
        return Collections.singletonList(new SimpleGrantedAuthority(user.getUserType().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Добавьте свою логику проверки, если нужно
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Добавьте свою логику проверки, если нужно
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Добавьте свою логику проверки, если нужно
    }

    @Override
    public boolean isEnabled() {
        // Можно добавить поле `active` в сущность User и проверять его
        return true;
    }
}
