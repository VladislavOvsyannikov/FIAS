package system.dao;

import org.springframework.stereotype.Repository;
import system.model.secondary.AddressObjectType;

@Repository
public class AddressObjectTypeDao extends GenericDao<AddressObjectType> {

    public String getFullName(int level, String shortName){
        return getEntity(
                "select * from adress_object_type where level="+level+" and scname=\""+shortName+"\" limit 1",
                AddressObjectType.class).getSOCRNAME().toLowerCase();
    }
}
