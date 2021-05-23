package com.university.itis.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.university.itis.exceptions.InvalidTokenException;
import com.university.itis.exceptions.NotFoundException;
import com.university.itis.model.User;
import com.university.itis.services.SecurityService;
import com.university.itis.utils.ErrorEntity;
import com.university.itis.utils.Result;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.hasText;

@Component
@AllArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private static final String AUTHORIZATION = "Authorization";
    private final SecurityService securityService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        try {
            User user = securityService.getByAuthToken(token);
            servletRequest.setAttribute("user", user);
            Collection<? extends GrantedAuthority> authorities = getAuthorities(user);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (InvalidTokenException | NotFoundException ignored) {}
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return user.getRoles()
                .stream()
                .map( (role) -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    public static String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
