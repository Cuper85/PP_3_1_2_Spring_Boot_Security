package ru.kata.spring.boot_security.demo.configs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (!roles.isEmpty()) {
            if (roles.contains("ROLE_ADMIN")) {
                response.sendRedirect("/admin");
            } else {
                response.sendRedirect("/user");
            }
        } else {
            response.sendRedirect("/");
        }
    }
}