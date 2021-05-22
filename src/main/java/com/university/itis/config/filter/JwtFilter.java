package com.university.itis.config.filter;

import com.university.itis.model.User;
import com.university.itis.services.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;

@Component
public class JwtFilter extends GenericFilterBean {

    private static final String AUTHORIZATION = "Authorization";

    private final JwtHelper jwtHelper;
    private final UserService userService;

    public JwtFilter(JwtHelper jwtHelper, UserService userService) {
        this.jwtHelper = jwtHelper;
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        if (token != null && jwtHelper.validateToken(token)) {
            String email = jwtHelper.getEmailFromToken(token);
            String password = jwtHelper.getPasswordFromToken(token);
            Optional<User> optionalUser = userService.findOneByEmail(email);
            if(optionalUser.isPresent() && optionalUser.get().getPassword().equals(password)) {
                User user = optionalUser.get();
                servletRequest.setAttribute("user", user);
                Collection<? extends GrantedAuthority> authorities = jwtHelper.getAuthorities(user);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public static String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
