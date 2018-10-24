package system.dao;

import org.springframework.stereotype.Repository;
import system.model.EstateStatus;

@Repository
public class EstateStatusDao extends GenericDao<EstateStatus> {

    public String getEstateType(int number){
        return getEntity(
                "select * from estate_status where eststatid="+number, EstateStatus.class).getNAME().toLowerCase();
    }
}
