package system.service;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Test {

    public static void main(String[] args){
        Test test = new Test();


        System.out.println(BCrypt.hashpw("123", BCrypt.gensalt()));
        System.out.println(BCrypt.hashpw("123", BCrypt.gensalt()));
        System.out.println(BCrypt.hashpw("123", BCrypt.gensalt()));
        System.out.println(BCrypt.hashpw("123", BCrypt.gensalt()));
        System.out.println(BCrypt.hashpw("123", BCrypt.gensalt()));
        System.out.println(BCrypt.hashpw("123", BCrypt.gensalt()));


        if (BCrypt.checkpw("123", BCrypt.hashpw("123", BCrypt.gensalt())))
            System.out.println("It matches");
        else System.out.println("It does not match");

        if (BCrypt.checkpw("123", BCrypt.hashpw("123", BCrypt.gensalt())))
            System.out.println("It matches");
        else System.out.println("It does not match");

        if (BCrypt.checkpw("123", BCrypt.hashpw("123", BCrypt.gensalt())))
            System.out.println("It matches");
        else System.out.println("It does not match");

        if (BCrypt.checkpw("123", BCrypt.hashpw("123", BCrypt.gensalt())))
            System.out.println("It matches");
        else System.out.println("It does not match");


    }
}
