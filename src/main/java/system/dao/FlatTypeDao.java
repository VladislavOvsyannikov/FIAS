package system.dao;

import org.springframework.stereotype.Repository;
import system.model.FlatType;

@Repository
public class FlatTypeDao extends GenericDao<FlatType>{

    public String getFlatType(int number){
        if (number == 2) return "квартира";
        return getEntity(
                "select * from flat_type where fltypeid="+number, FlatType.class).getNAME().toLowerCase();
    }
}
