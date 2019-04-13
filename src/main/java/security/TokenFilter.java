package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.nonNull;

public class TokenFilter extends OncePerRequestFilter {

    private TokenManager tokenManager;

    @Autowired
    public void setTokenManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/fias")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (nonNull(authentication)) SecurityContextHolder.getContext()
                    .setAuthentication(tokenManager.authenticate(authentication));
        }
        filterChain.doFilter(request, response);
    }
}