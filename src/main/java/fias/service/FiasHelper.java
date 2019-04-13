package fias.service;

import fias.domain.AbstractFiasObject;
import fias.domain.AddrObject;
import fias.domain.House;
import fias.domain.Room;
import fias.domain.Stead;
import fias.repository.AddrObjectRepository;
import fias.repository.AddressObjectTypeRepository;
import fias.repository.EstateStatusRepository;
import fias.repository.FlatTypeRepository;
import fias.repository.HouseRepository;
import fias.repository.HouseStateStatusRepository;
import fias.repository.RoomRepository;
import fias.repository.SteadRepository;
import fias.repository.StructureStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Log4j2
@Service
@RequiredArgsConstructor
public class FiasHelper {

    private final AddressObjectTypeRepository addressObjectTypeRepository;
    private final EstateStatusRepository estateStatusRepository;
    private final StructureStatusRepository structureStatusRepository;
    private final FlatTypeRepository flatTypeRepository;
    private final HouseStateStatusRepository houseStateStatusRepository;
    private final AddrObjectRepository addrObjectRepository;
    private final HouseRepository houseRepository;
    private final RoomRepository roomRepository;
    private final SteadRepository steadRepository;
    private final EntityManager entityManager;

    String getFullAddrObjectType(AddrObject addrObject) {
        if (addrObject.getFORMALNAME().contains("Чувашская")) return "республика";
        if (addrObject.getAOLEVEL() == 7) {
            switch (addrObject.getSHORTNAME()) {
                case "ул":
                    return "улица";
                case "пер":
                    return "переулок";
                case "проезд":
                    return "проезд";
                case "пр-кт":
                    return "проспект";
                case "линия":
                    return "линия";
                case "аллея":
                    return "аллея";
                case "тер":
                    return "территория";
                case "пл":
                    return "площадь";
                case "сад":
                    return "сад";
                case "наб":
                    return "набережная";
            }
        }
        return addressObjectTypeRepository
                .getFullAddrObjectType(addrObject.getAOLEVEL(), addrObject.getSHORTNAME()).toLowerCase();
    }

    String getHouseType(House house) {
        if (house.getESTSTATUS() != 0) {
            if (house.getESTSTATUS() == 2) return "дом";
            return estateStatusRepository.getEstateType(house.getESTSTATUS()).toLowerCase();
        } else if (house.getSTRSTATUS() != 0)
            return structureStatusRepository.getStructureType(house.getSTRSTATUS()).toLowerCase();
        return "";
    }

    String getRoomType(Room room) {
        if (room.getFLATTYPE() == 2) return "квартира";
        return flatTypeRepository.getFlatType(room.getFLATTYPE()).toLowerCase();
    }

    String getHouseStateStatus(House house) {
        String name = houseStateStatusRepository.getHouseStateStatus(house.getSTATSTATUS())
                .replace("-", "").replace("+", "");
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        if (name.contains("(")) name = name.substring(0, name.indexOf('(') - 1);
        return name;
    }

    String getHouseName(House house) {
        String name = "";
        if (nonNull(house.getHOUSENUM())) name += house.getHOUSENUM();
        if (nonNull(house.getBUILDNUM())) name += " к. " + house.getBUILDNUM();
        if (nonNull(house.getSTRUCNUM())) name += " стр. " + house.getSTRUCNUM();
        if (isNull(house.getHOUSENUM()) && isNull(house.getBUILDNUM()) && nonNull(house.getSTRUCNUM()))
            name = house.getSTRUCNUM();
        return name;
    }

    String getFullAddress(AddrObject addrObject, String postalcode) {
        if (addrObject.getFORMALNAME().contains("Чувашская")) addrObject.setFORMALNAME("Чувашская");
        StringBuilder fullAddress = new StringBuilder(getFullAddrObjectType(addrObject) + " " + addrObject.getFORMALNAME());
        while (nonNull(addrObject.getPARENTGUID())) {
            addrObject = getAddrObjectByGuid(addrObject.getPARENTGUID());
            if (addrObject.getFORMALNAME().contains("Чувашская")) addrObject.setFORMALNAME("Чувашская");
            fullAddress.insert(0, getFullAddrObjectType(addrObject) + " " + addrObject.getFORMALNAME() + ", ");
        }
        return isNull(postalcode) ? fullAddress.toString() : postalcode + ", " + fullAddress;
    }

    String getFullAddress(House house, String postalcode) {
        AddrObject addrObject = getAddrObjectByGuid(house.getAOGUID());
        return getFullAddress(addrObject, postalcode) + ", " + getHouseType(house) + " " + getHouseName(house);
    }

    String getFullAddress(Stead stead) {
        AddrObject addrObject = getAddrObjectByGuid(stead.getPARENTGUID());
        return getFullAddress(addrObject, stead.getPOSTALCODE()) + ", участок " + stead.getNUMBER();
    }

    String getFullAddress(Room room) {
        House house = getHouseByGuid(room.getHOUSEGUID());
        room.setOKATO(house.getOKATO());
        room.setOKTMO(house.getOKTMO());
        room.setIFNSFL(house.getIFNSFL());
        room.setIFNSUL(house.getIFNSUL());
        room.setHouseStateStatus(getHouseStateStatus(house));
        return getFullAddress(house, room.getPOSTALCODE()) + ", " + getRoomType(room) + " " + room.getFLATNUMBER();
    }

    AddrObject getAddrObjectByGuid(String guid) {
        List<AddrObject> addrObjects = addrObjectRepository.getAddrObjectsByAoguid(guid);
        return !addrObjects.isEmpty() ? getObjectsWithMaxEnddate(addrObjects).get(0) : null;
    }

    House getHouseByGuid(String guid) {
        List<House> houses = houseRepository.getHousesByHouseguid(guid);
        return !houses.isEmpty() ? getObjectsWithMaxEnddate(houses).get(0) : null;
    }

    Room getRoomByGuid(String guid) {
        List<Room> rooms = roomRepository.getRoomsByRoomguid(guid);
        return !rooms.isEmpty() ? getObjectsWithMaxEnddate(rooms).get(0) : null;
    }

    Stead getSteadByGuid(String guid) {
        List<Stead> steads = steadRepository.getSteadsBySteadguid(guid);
        return !steads.isEmpty() ? getObjectsWithMaxEnddate(steads).get(0) : null;
    }


    List<Stead> getSteadsByParams(LinkedHashMap<String, String> params, Boolean isActual) {
        List<Stead> steads = entityManager.createQuery("select o from Stead o where "
                + getQueryPart(params, isActual, "STEAD"), Stead.class).getResultList();
        if (!isActual) steads = getObjectsWithMaxEnddate(steads);
        for (Stead stead : steads) stead.setFullAddress(getFullAddress(stead));
        return steads;
    }

    List<Room> getRoomsByParams(LinkedHashMap<String, String> params, Boolean isActual) {
        params.remove("okato");
        params.remove("oktmo");
        params.remove("ifnsfl");
        params.remove("ifnsul");
        List<Room> rooms = entityManager.createQuery("select o from Room o where "
                + getQueryPart(params, isActual, "ROOM"), Room.class).getResultList();
        if (!isActual) rooms = getObjectsWithMaxEnddate(rooms);
        for (Room room : rooms) room.setFullAddress(getFullAddress(room));
        return rooms;
    }

    List<House> getHousesByParams(LinkedHashMap<String, String> params, Boolean isActual) {
        List<House> houses = entityManager.createQuery("select o from House o where "
                + getQueryPart(params, false, "HOUSE"), House.class).getResultList();
        if (isActual) {
            int currentDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
            houses.removeIf(house -> currentDate > house.getENDDATE());
        } else houses = getObjectsWithMaxEnddate(houses);
        for (House house : houses) {
            house.setFullAddress(getFullAddress(house, house.getPOSTALCODE()));
            house.setHouseStateStatus(getHouseStateStatus(house));
        }
        return houses;
    }

    List<AddrObject> getAddrObjectsByParams(LinkedHashMap<String, String> params, Boolean isActual) {
        String cadnum = params.remove("cadnum");
        List<AddrObject> addrObjects = entityManager.createQuery("select o from AddrObject o where "
                + getQueryPart(params, isActual, "AO"), AddrObject.class).getResultList();
        if (!isActual) addrObjects = getObjectsWithMaxEnddate(addrObjects);
        for (AddrObject addrObject : addrObjects)
            addrObject.setFullAddress(getFullAddress(addrObject, addrObject.getPOSTALCODE()));
        if (nonNull(cadnum)) params.put("cadnum", cadnum);
        return addrObjects;
    }

    String getQueryPart(LinkedHashMap<String, String> params, Boolean isActual, String guidType) {
        List<String> strings = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getKey().equals("guid")) strings.add("o." + guidType + "GUID='" + entry.getValue() + "'");
            else strings.add("o." + entry.getKey().toUpperCase() + "='" + entry.getValue() + "'");
        }
        if (isActual) strings.add("o.LIVESTATUS=1");
        return String.join(" and ", strings);
    }

    <T extends AbstractFiasObject> List<T> getObjectsWithMaxEnddate(List<T> objects) {
        if (objects.size() <= 1) return objects;
        objects.sort(Comparator.comparing(AbstractFiasObject::getGuid));
        List<T> res = new ArrayList<>();
        T maxObject = objects.get(0);
        T currentObject;
        for (int i = 1; i < objects.size(); i++) {
            currentObject = objects.get(i);
            if (maxObject.getGuid().equals(currentObject.getGuid())) {
                if (maxObject.getENDDATE() < currentObject.getENDDATE()) maxObject = currentObject;
            } else {
                res.add(maxObject);
                maxObject = currentObject;
            }
            if (i == objects.size() - 1) res.add(maxObject);
        }
        return res;
    }
}
