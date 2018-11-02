package system.dao;

import org.springframework.stereotype.Repository;
import system.model.EstateStatus;

@Repository
public class EstateStatusDao extends GenericDao<EstateStatus> {

    public String getEstateType(int number){
        if (number == 2) return "дом";
        return getEntity(
                "select * from estate_status where eststatid="+number, EstateStatus.class).getNAME().toLowerCase();
    }
}
