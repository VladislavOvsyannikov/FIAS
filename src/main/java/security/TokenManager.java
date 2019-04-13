package security;

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
import security.user.User;
import security.user.UserRepository;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class TokenManager implements AuthenticationManager {

    private static final SecretKey KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof UsernamePasswordAuthenticationToken)
            return createNewTokenAuthentication(authentication);
        if (authentication instanceof Token)
            return checkTokenAuthentication((Token) authentication);
        return null;
    }

    private Authentication checkTokenAuthentication(Token authentication) {
        String token = authentication.getToken();
        Claims claims = (DefaultClaims) Jwts.parser().setSigningKey(KEY).parse(token).getBody();
        if (claims.get("endDate", Long.class) <= System.currentTimeMillis()) return null;
        return authentication;
    }

    private Authentication createNewTokenAuthentication(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userRepository.findByName(username).orElse(null);
        if (isNull(user) || !user.getIsEnable() || !BCrypt.checkpw(password, user.getPassword())) return null;
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("username", username);
        tokenData.put("role", user.getRole());
        tokenData.put("isEnable", user.getIsEnable());
        tokenData.put("endDate", System.currentTimeMillis() + 24 * 3600 * 1000);
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setClaims(tokenData);
        String token = jwtBuilder.signWith(KEY, SignatureAlgorithm.HS512).compact();
        List<GrantedAuthority> grantedAuth = new ArrayList<>();
        grantedAuth.add(new SimpleGrantedAuthority(user.getRole()));
        if (user.getRole().equals("ROLE_ADMIN")) grantedAuth.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new Token(token, grantedAuth, true);
    }

    public List<String> getCurrentUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof Token)) return null;
        List<String> info = new ArrayList<>();
        String token = ((Token) authentication).getToken();
        Claims claims = (DefaultClaims) Jwts.parser().setSigningKey(KEY).parse(token).getBody();
        info.add(claims.get("username", String.class));
        info.add(claims.get("role", String.class));
        return info;
    }
}
