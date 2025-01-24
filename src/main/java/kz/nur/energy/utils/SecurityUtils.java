package kz.nur.energy.utils;

import kz.nur.energy.entity.User;
import kz.nur.energy.service.auth.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof CustomUserDetails) {
                return ((CustomUserDetails) principal).getUser();
            }
        }

        return null;
    }

    public static boolean isAdmin() {
        User currentUser = getCurrentUser();
        return currentUser != null && currentUser.getUserType().equals("ADMIN");
    }

    public static boolean hasRole(String role) {
        User currentUser = getCurrentUser();
        return currentUser != null && currentUser.getUserType().name().equals(role);
    }
}
