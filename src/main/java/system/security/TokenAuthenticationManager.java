package system.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import system.domain.User;
import system.repository.UserRepository;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class TokenAuthenticationManager implements AuthenticationManager {

    private static final SecretKey KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof UsernamePasswordAuthenticationToken)
            return createNewTokenAuthentication(authentication);
        if (authentication instanceof TokenAuthentication)
            return checkTokenAuthentication((TokenAuthentication) authentication);
        return null;
    }

    private Authentication checkTokenAuthentication(TokenAuthentication authentication) {
        String token = authentication.getToken();
        Claims claims = (DefaultClaims) Jwts.parser().setSigningKey(KEY).parse(token).getBody();
        if (claims.get("endDate", Long.class) > new Date().getTime()) {
            return authentication;
        } else return null;
    }

    private Authentication createNewTokenAuthentication(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userRepository.findByName(username).orElse(null);
        if (nonNull(user) && user.getIsEnable() && BCrypt.checkpw(password, user.getPassword())) {
            Map<String, Object> tokenData = new HashMap<>();
            tokenData.put("username", username);
            tokenData.put("role", user.getRole());
            tokenData.put("isEnable", user.getIsEnable());
            tokenData.put("endDate", new Date().getTime() + 24 * 3600 * 1000);
            JwtBuilder jwtBuilder = Jwts.builder();
            jwtBuilder.setClaims(tokenData);
            String token = jwtBuilder.signWith(KEY, SignatureAlgorithm.HS512).compact();
            List<GrantedAuthority> grantedAuth = new ArrayList<>();
            grantedAuth.add(new SimpleGrantedAuthority(user.getRole()));
            if (user.getRole().equals("ROLE_ADMIN")) grantedAuth.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new TokenAuthentication(token, grantedAuth, true);
        } else return null;
    }

    public List<String> getCurrentUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof TokenAuthentication) {
            List<String> res = new ArrayList<>();
            String token = ((TokenAuthentication) authentication).getToken();
            Claims claims = (DefaultClaims) Jwts.parser().setSigningKey(KEY).parse(token).getBody();
            res.add(claims.get("username", String.class));
            res.add(claims.get("role", String.class));
            return res;
        } else return null;
    }
}
