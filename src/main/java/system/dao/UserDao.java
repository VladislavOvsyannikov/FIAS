package system.dao;

import org.springframework.stereotype.Repository;
import system.model.User;

import java.util.List;

@Repository
public class UserDao extends GenericDao<User> {

    public List<User> getAllUsersWithoutPasswords() {
        List<User> users = getEntities("select * from user", User.class);
        for (User user : users) user.setPassword(null);
        return users;
    }

    public User getUser(String name){
        return getEntity("select * from user where name=\""+name+"\"", User.class);
    }
}
