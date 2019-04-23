package security.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import security.TokenManager;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TokenManager tokenManager;

    public Boolean signIn(String name, String password) {
        Authentication authentication = tokenManager.authenticate(new UsernamePasswordAuthenticationToken(name, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return nonNull(authentication);
    }

    public boolean signUp(String name, String password) {
        if (name.replaceAll(" ", "").equals("") ||
                password.replaceAll(" ", "").equals("")) return false;
        User oldUser = userRepository.findByName(name).orElse(null);
        if (isNull(oldUser)) {
            User newUser = new User();
            newUser.setName(name);
            newUser.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            newUser.setRole("ROLE_USER");
            newUser.setIsEnable(true);
            userRepository.save(newUser);
            return true;
        }
        return false;
    }

    public List<UserDto> getAllUsersWithoutPasswords() {
        return userMapper.toDto(userRepository.getByRole("ROLE_USER"));
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public void blockUser(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        if (isNull(user)) return;
        user.setIsEnable(!user.getIsEnable());
        userRepository.save(user);
    }

    public List<String> getCurrentUserInfo() {
        return tokenManager.getCurrentUserInfo();
    }
}
