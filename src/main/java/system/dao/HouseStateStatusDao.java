package system.dao;

import org.springframework.stereotype.Repository;
import system.model.HouseStateStatus;

@Repository
public class HouseStateStatusDao extends GenericDao<HouseStateStatus>{

    public String getStateStatus(int number){
        String name = getEntity(
                "select * from house_state_status where housestid="+number, HouseStateStatus.class)
                .getNAME().replace("-","").replace("+","");
        name = name.substring(0,1).toUpperCase() + name.substring(1);
        if (name.contains("(")) name = name.substring(0, name.indexOf("(")-1);
        return name;
    }
}
