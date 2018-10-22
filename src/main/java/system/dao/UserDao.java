package system.dao;

import org.springframework.stereotype.Repository;
import system.model.primary.User;

import java.util.List;

@Repository
public class UserDao extends GenericDao<User> {

    public List<User> getAllUsers(){
        return getEntities("select * from user", User.class);
    }
}
