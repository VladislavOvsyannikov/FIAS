package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import system.service.TokenAuthenticationManager;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.nonNull;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private TokenAuthenticationManager tokenAuthenticationManager;

    @Autowired
    public void setTokenAuthenticationManager(TokenAuthenticationManager tokenAuthenticationManager) {
        this.tokenAuthenticationManager = tokenAuthenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/fias")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (nonNull(authentication)) SecurityContextHolder.getContext()
                    .setAuthentication(tokenAuthenticationManager.authenticate(authentication));
        }
        filterChain.doFilter(request, response);
    }
}