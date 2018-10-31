package system.dao;

import org.springframework.stereotype.Repository;
import system.model.User;

import java.util.List;

@Repository
public class UserDao extends GenericDao<User> {

    public List<User> getAllUsers(){
        return getEntities("select * from user", User.class);
    }

    public User getUser(String name){
        return getEntity("select * from user where name=\""+name+"\"", User.class);
    }
}
