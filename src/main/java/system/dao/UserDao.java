package system.dao;

import org.springframework.stereotype.Repository;
import system.model.User;

import java.util.List;

@Repository
public class UserDao extends GenericDao<User> {

    public List<User> getAllUsersWithoutPasswords() {
        List<User> users = getEntities("select * from user where role=\"ROLE_USER\"", User.class);
        for (User user : users) user.setPassword("");
        return users;
    }

    public User getUser(String name){
        return getEntity("select * from user where name=\""+name+"\"", User.class);
    }

    public void deleteUser(User user){
        remove(user);
    }

    public void blockUser(int id) {
        User user = getUser(id);
        user.setIsEnable(!user.getIsEnable());
        saveOrUpdate(user);
    }

    private User getUser(int id) {
        return getEntity("select * from user where id="+id, User.class);
    }
}
