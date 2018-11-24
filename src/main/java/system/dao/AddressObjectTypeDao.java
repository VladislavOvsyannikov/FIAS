package system.dao;

import org.springframework.stereotype.Repository;
import system.model.AddressObjectType;

@Repository
public class AddressObjectTypeDao extends GenericDao<AddressObjectType> {

    public String getFullName(int level, String shortName){
        if (level == 7){
            switch (shortName){
                case "ул": return "улица";
                case "пер": return "переулок";
                case "проезд": return "проезд";
                case "пр-кт": return "проспект";
                case "линия": return "линия";
                case "аллея": return "аллея";
                case "тер": return "территория";
                case "пл": return "площадь";
                case "сад": return "сад";
                case "наб": return "набережная";
            }
        }
        return getEntity(
                "select * from adress_object_type where level="+level+" and scname='"+shortName+"' limit 1",
                AddressObjectType.class).getSOCRNAME().toLowerCase();
    }
}
