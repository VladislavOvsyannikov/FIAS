package system.dao;

import org.springframework.stereotype.Repository;
import system.model.secondary.StructureStatus;

@Repository
public class StructureStatusDao extends GenericDao<StructureStatus> {

    public String getStructureType(int number){
        return getEntity("select * from structure_status where strstatid="+number,
                StructureStatus.class).getNAME().toLowerCase();
    }
}
