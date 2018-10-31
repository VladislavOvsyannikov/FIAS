package system.service;


import org.springframework.security.crypto.bcrypt.BCrypt;

public class Test {

    public static void main(String[] args) {
        System.out.print(BCrypt.hashpw("1", BCrypt.gensalt()));
    }
}
