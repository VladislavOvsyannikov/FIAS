package fias.service;

import fias.domain.AddrObject;
import fias.domain.AddressObjectType;
import fias.domain.EstateStatus;
import fias.domain.FlatType;
import fias.domain.House;
import fias.domain.HouseStateStatus;
import fias.domain.Room;
import fias.domain.Stead;
import fias.domain.StructureStatus;
import fias.repository.AddrObjectRepository;
import fias.repository.AddressObjectTypeRepository;
import fias.repository.EstateStatusRepository;
import fias.repository.FlatTypeRepository;
import fias.repository.HouseRepository;
import fias.repository.HouseStateStatusRepository;
import fias.repository.RoomRepository;
import fias.repository.SteadRepository;
import fias.repository.StructureStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;

import static org.testng.Assert.assertEquals;

@Configuration
@WebAppConfiguration
@EnableAutoConfiguration
@ComponentScan({"fias", "security"})
@EntityScan(basePackages = {"fias.domain", "security.user"})
@EnableJpaRepositories(basePackages = {"fias.repository", "security.user"})
public class FiasHelperIT extends AbstractTestNGSpringContextTests {

    @Autowired
    private FiasHelper fiasHelper;
    @Autowired
    private AddressObjectTypeRepository addressObjectTypeRepository;
    @Autowired
    private EstateStatusRepository estateStatusRepository;
    @Autowired
    private StructureStatusRepository structureStatusRepository;
    @Autowired
    private FlatTypeRepository flatTypeRepository;
    @Autowired
    private HouseStateStatusRepository houseStateStatusRepository;
    @Autowired
    private AddrObjectRepository addrObjectRepository;
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private SteadRepository steadRepository;

    @AfterMethod
    public void delete() {
        addressObjectTypeRepository.deleteAll();
        estateStatusRepository.deleteAll();
        structureStatusRepository.deleteAll();
        flatTypeRepository.deleteAll();
        houseStateStatusRepository.deleteAll();
        addrObjectRepository.deleteAll();
        houseRepository.deleteAll();
        roomRepository.deleteAll();
        steadRepository.deleteAll();
    }

    @Test
    public void getFullAddrObjectTypeTest() {
        addressObjectTypeRepository.save(createAddressObjectType());
        AddrObject addrObject = createAddrObject(null, null, "Чувашская респ", 7, "ул");

        assertEquals(fiasHelper.getFullAddrObjectType(addrObject), "республика");

        addrObject.setFORMALNAME("Спб");
        assertEquals(fiasHelper.getFullAddrObjectType(addrObject), "улица");

        addrObject.setSHORTNAME("пер");
        assertEquals(fiasHelper.getFullAddrObjectType(addrObject), "переулок");

        addrObject.setSHORTNAME("проезд");
        assertEquals(fiasHelper.getFullAddrObjectType(addrObject), "проезд");

        addrObject.setSHORTNAME("пр-кт");
        assertEquals(fiasHelper.getFullAddrObjectType(addrObject), "проспект");

        addrObject.setSHORTNAME("линия");
        assertEquals(fiasHelper.getFullAddrObjectType(addrObject), "линия");

        addrObject.setSHORTNAME("аллея");
        assertEquals(fiasHelper.getFullAddrObjectType(addrObject), "аллея");

        addrObject.setSHORTNAME("тер");
        assertEquals(fiasHelper.getFullAddrObjectType(addrObject), "территория");

        addrObject.setSHORTNAME("пл");
        assertEquals(fiasHelper.getFullAddrObjectType(addrObject), "площадь");

        addrObject.setSHORTNAME("сад");
        assertEquals(fiasHelper.getFullAddrObjectType(addrObject), "сад");

        addrObject.setSHORTNAME("наб");
        assertEquals(fiasHelper.getFullAddrObjectType(addrObject), "набережная");

        addrObject.setSHORTNAME("пос");
        assertEquals(fiasHelper.getFullAddrObjectType(addrObject), "посёлок");
    }

    @Test
    public void getHouseTypeTest() {
        estateStatusRepository.save(createEstateStatus());
        structureStatusRepository.save(createStructureStatus());
        House house = createHouse(null, null, null, null, null, 2, 1, null);

        assertEquals(fiasHelper.getHouseType(house), "дом");

        house.setESTSTATUS(1);
        assertEquals(fiasHelper.getHouseType(house), "дом2");

        house.setESTSTATUS(0);
        assertEquals(fiasHelper.getHouseType(house), "сооружение");
    }

    @Test
    public void getRoomTypeTest() {
        flatTypeRepository.save(createFlatType());
        Room room = createRoom(null, null, 2, 0);

        assertEquals(fiasHelper.getRoomType(room), "квартира");

        room.setFLATTYPE(1);
        assertEquals(fiasHelper.getRoomType(room), "помещение");
    }

    @Test
    public void getHouseStateStatusTest() {
        houseStateStatusRepository.save(createHouseStateStatus());
        House house = createHouse(null, null, null, null, null, 0, 0, null);

        assertEquals(fiasHelper.getHouseStateStatus(house), "Без особого состояния");
    }

    @Test
    public void getHouseNameTest() {
        House house = createHouse(null, null, "1", "2", "3", 0, 0, null);

        assertEquals(fiasHelper.getHouseName(house), "1 к. 2 стр. 3");

        house = createHouse(null, null, null, null, "3", 0, 0, null);
        assertEquals(fiasHelper.getHouseName(house), "3");
    }

    @Test
    public void getAddrObjectByGuidTest() {
        AddrObject addrObject = createAddrObject("1", "123", null, 0, null);
        addrObjectRepository.save(addrObject);

        assertEquals(fiasHelper.getAddrObjectByGuid("123"), addrObject);
    }

    @Test
    public void getHouseByGuidTest() {
        House house = createHouse("1", "123", null, null, null, 0, 0, null);
        houseRepository.save(house);

        assertEquals(fiasHelper.getHouseByGuid("123"), house);
    }

    @Test
    public void getRoomByGuidTest() {
        Room room = createRoom("1", "123", 0, 0);
        roomRepository.save(room);

        assertEquals(fiasHelper.getRoomByGuid("123"), room);
    }

    @Test
    public void getSteadByGuidTest() {
        Stead stead = createStead(null);
        steadRepository.save(stead);

        assertEquals(fiasHelper.getSteadByGuid("123"), stead);
    }

    @Test
    public void getQueryPartTest() {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("guid", "123");

        assertEquals(fiasHelper.getQueryPart(params, true, "AO"), "o.AOGUID='123' and o.LIVESTATUS=1");
    }

    @Test
    public void getAddrObjectsByParamsTest() {
        AddrObject addrObject = createAddrObject("1", "123", "Чувашская", 0, null);
        addrObjectRepository.save(addrObject);
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("guid", "123");

        assertEquals(fiasHelper.getAddrObjectsByParams(params, false), Collections.singletonList(addrObject));
    }

    @Test
    public void getSteadsByParamsTest() {
        addrObjectRepository.save(createAddrObject("1", "123", "Чувашская", 0, null));
        Stead stead = createStead("123");
        steadRepository.save(stead);
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("guid", "123");

        assertEquals(fiasHelper.getSteadsByParams(params, false), Collections.singletonList(stead));
    }

    @Test
    public void getHousesByParamsTest() {
        addrObjectRepository.save(createAddrObject("1", "123", "Чувашская", 0, null));
        houseStateStatusRepository.save(createHouseStateStatus());
        House house = createHouse("1", "123", null, null, null, 2, 2, "123");
        houseRepository.save(house);
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("guid", "123");

        assertEquals(fiasHelper.getHousesByParams(params, false), Collections.singletonList(house));
    }

    @Test
    public void getRoomsByParamsTest() {
        addrObjectRepository.save(createAddrObject("1", "123", "Чувашская", 0, null));
        houseStateStatusRepository.save(createHouseStateStatus());
        houseRepository.save(createHouse("1", "123", null, null, null, 2, 2, "123"));
        Room room = createRoom("1", "123", 2, 0);
        roomRepository.save(room);
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("guid", "123");

        assertEquals(fiasHelper.getRoomsByParams(params, false), Collections.singletonList(room));
    }

    @Test
    public void getObjectsWithMaxEnddateTest() {
        Room room1 = createRoom("1", "123", 0, 20500000);
        Room room2 = createRoom("2", "123", 0, 20300000);
        Room room3 = createRoom("3", "123", 0, 20400000);
        Room room4 = createRoom("4", "124", 0, 20500000);

        assertEquals(fiasHelper.getObjectsWithMaxEnddate(Arrays.asList(room1, room2, room3, room4)),
                Arrays.asList(room1, room4));
    }


    private AddrObject createAddrObject(String id, String guid, String formalName, int aoLevel, String shortName) {
        AddrObject addrObject = new AddrObject();
        addrObject.setAOID(id);
        addrObject.setAOGUID(guid);
        addrObject.setFORMALNAME(formalName);
        addrObject.setAOLEVEL(aoLevel);
        addrObject.setSHORTNAME(shortName);
        return addrObject;
    }

    private House createHouse(String id, String guid, String num1, String num2,
                              String num3, int est, int str, String parentGuid) {
        House house = new House();
        house.setHOUSEID(id);
        house.setHOUSEGUID(guid);
        house.setHOUSENUM(num1);
        house.setBUILDNUM(num2);
        house.setSTRUCNUM(num3);
        house.setESTSTATUS(est);
        house.setSTRSTATUS(str);
        house.setSTATSTATUS(0);
        house.setAOGUID(parentGuid);
        return house;
    }

    private Room createRoom(String id, String guid, int type, int endDate) {
        Room room = new Room();
        room.setROOMID(id);
        room.setROOMGUID(guid);
        room.setFLATTYPE(type);
        room.setHOUSEGUID("123");
        room.setENDDATE(endDate);
        return room;
    }

    private Stead createStead(String parentGuid) {
        Stead stead = new Stead();
        stead.setSTEADID("1");
        stead.setSTEADGUID("123");
        stead.setPARENTGUID(parentGuid);
        return stead;
    }

    private HouseStateStatus createHouseStateStatus() {
        HouseStateStatus houseStateStatus = new HouseStateStatus();
        houseStateStatus.setHOUSESTID(0);
        houseStateStatus.setNAME("без особого состояния");
        return houseStateStatus;
    }

    private EstateStatus createEstateStatus() {
        EstateStatus estateStatus = new EstateStatus();
        estateStatus.setESTSTATID(1);
        estateStatus.setNAME("дом2");
        return estateStatus;
    }

    private StructureStatus createStructureStatus() {
        StructureStatus structureStatus = new StructureStatus();
        structureStatus.setSTRSTATID(1);
        structureStatus.setNAME("сооружение");
        return structureStatus;
    }

    private AddressObjectType createAddressObjectType() {
        AddressObjectType addressObjectType = new AddressObjectType();
        addressObjectType.setKOD_T_ST("1");
        addressObjectType.setLEVEL(7);
        addressObjectType.setSCNAME("пос");
        addressObjectType.setSOCRNAME("посёлок");
        return addressObjectType;
    }

    private FlatType createFlatType() {
        FlatType flatType = new FlatType();
        flatType.setFLTYPEID(1);
        flatType.setNAME("помещение");
        return flatType;
    }
}
