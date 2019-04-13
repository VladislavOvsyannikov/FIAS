package fias.service;

import fias.domain.Version;
import fias.dto.AddrObjectDto;
import fias.dto.HouseDto;
import fias.dto.RoomDto;
import fias.dto.SteadDto;
import fias.repository.AddrObjectRepository;
import fias.repository.FlatTypeRepository;
import fias.repository.HouseStateStatusRepository;
import fias.repository.RoomRepository;
import fias.repository.SteadRepository;
import fias.repository.VersionRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.ResourceUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.assertj.core.api.Java6Assertions.assertThat;

@Configuration
@WebAppConfiguration
@EnableAutoConfiguration
@ComponentScan({"fias", "security"})
@EntityScan(basePackages = {"fias.domain", "security.user"})
@EnableJpaRepositories(basePackages = {"fias.repository", "security.user"})
public class FiasServiceIT extends AbstractTestNGSpringContextTests {

    @Autowired
    private FiasService fiasService;
    @Autowired
    private VersionRepository versionRepository;
    @Autowired
    private Installer installer;
    @Autowired
    private SteadRepository steadRepository;
    @Autowired
    private AddrObjectRepository addrObjectRepository;
    @Autowired
    private FlatTypeRepository flatTypeRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HouseStateStatusRepository houseStateStatusRepository;
    private String guidPart = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    @BeforeClass
    @SneakyThrows
    public void installFiasComplete01() {
        File folder = ResourceUtils.getFile("classpath:complete01");
        this.installer.installDatabase(folder, DatabaseAction.SAVE, "01", 10);
    }

    @AfterMethod
    public void delete() {
        versionRepository.deleteAll();
    }

    @Test
    public void getMainPathTest() {
        assertThat(fiasService.getMainPath().length()).isGreaterThan(0);
    }

    @Test
    public void getFiasModuleStatusTest() {
        assertThat(fiasService.getFiasModuleStatus()).isEqualTo(FiasModuleStatus.WORKING);
    }

    @Test
    public void getCurrentVersionTest() {
        versionRepository.save(createVersion());

        assertThat(fiasService.getCurrentVersion()).isEqualTo("20181000");
    }

    @Test
    public void getLastVersionTest() {
        assertThat(fiasService.getLastVersion().length()).isEqualTo(8);
    }

    @Test
    public void getNewVersionsTest() {
        versionRepository.save(createVersion());

        assertThat(fiasService.getListOfNewVersions().size()).isGreaterThan(10);
    }

    @Test
    public void installDatabaseTest() {
        assertThat(flatTypeRepository.count()).isEqualTo(10);
        assertThat(houseStateStatusRepository.count()).isEqualTo(43);
        assertThat(roomRepository.count()).isEqualTo(8);
        assertThat(addrObjectRepository.count()).isEqualTo(4);

    }

    @Test
    public void getAddrObjectsStartListTest() {
        List<AddrObjectDto> addrObjects = fiasService.getAddrObjectsStartList();

        assertThat(addrObjects.size()).isEqualTo(1);
        assertThat(addrObjects.get(0).getFORMALNAME()).isEqualTo("Спб");
    }

    @Test
    public void getAddrObjectsByNameTest() {
        List<AddrObjectDto> addrObjects = fiasService.getAddrObjectsByName("Лени", NameSearchType.CITY, false);

        assertThat(addrObjects.size()).isEqualTo(1);
        assertThat(addrObjects.get(0).getFORMALNAME()).isEqualTo("Ленинград");
        assertThat(addrObjects.get(0).getLIVESTATUS()).isEqualTo(0);

        addrObjects = fiasService.getAddrObjectsByName("Лени123", NameSearchType.ALL, false);

        assertThat(addrObjects.size()).isEqualTo(0);
    }

    @Test
    public void searchOldAddrObjectsByGuidTest() {
        CustomPair pair = fiasService.searchOldAddrObjectsByGuid("01-a" + guidPart);
        List<AddrObjectDto> old = pair.getOld();
        AddrObjectDto actual = pair.getActual();

        assertThat(old.size()).isEqualTo(1);
        assertThat(old.get(0).getFORMALNAME()).isEqualTo("Ленинград");
        assertThat(actual.getFORMALNAME()).isEqualTo("Спб");
    }

    @Test
    public void searchObjectsByParametersTest() {
        List<Object> objects = fiasService
                .searchObjectsByParameters("02-h" + guidPart, null, null, null, null, null, null, Collections.singletonList(ParameterSearchType.ALL), false);

        assertThat(objects.size()).isEqualTo(1);

        objects = fiasService
                .searchObjectsByParameters("02-h", null, null, null, null, null, null, null, null);

        assertThat(objects.size()).isEqualTo(0);
    }

    @Test
    public void getAddrObjectsByParentGuidTest() {
        List<AddrObjectDto> addrObjects = fiasService.getAddrObjectsByParentGuid("01-a" + guidPart, true);

        assertThat(addrObjects.size()).isEqualTo(2);

        addrObjects = fiasService.getAddrObjectsByParentGuid("01-a", false);

        assertThat(addrObjects.size()).isEqualTo(0);
    }

    @Test
    public void getSteadsByParentGuidTest() {
        List<SteadDto> steads = fiasService.getSteadsByParentGuid("03-a" + guidPart, true);

        assertThat(steads.size()).isEqualTo(4);
    }

    @Test
    public void getHousesByParentGuidTest() {
        List<HouseDto> houses = fiasService.getHousesByParentGuid("02-a" + guidPart, true);

        assertThat(houses.size()).isEqualTo(2);
    }

    @Test
    public void getRoomsListByParentGuidTest() {
        List<RoomDto> rooms = fiasService.getRoomsListByParentGuid("03-h" + guidPart, false);

        assertThat(rooms.size()).isEqualTo(3);
    }

    @Test
    public void searchObjectByGuidTest() {
        Object object = fiasService.searchObjectByGuid("03-s" + guidPart);

        assertThat(nonNull(object)).isTrue();
    }

    @Test
    public void getFullAddressByGuidTest() {
        String address = fiasService.getFullAddress("02-r" + guidPart);

        assertThat(address).isEqualTo("123654, город Спб, улица улица2, дом 4, квартира 30");

        address = fiasService.getFullAddress("02-r");

        assertThat(isNull(address)).isTrue();
    }

    @Test
    @SneakyThrows
    public void installFiasDelta02() {
        File folder = ResourceUtils.getFile("classpath:delta02");
        installer.installDatabase(folder, DatabaseAction.UPDATE, "02", 2);

        assertThat(steadRepository.count()).isEqualTo(6);
        assertThat(steadRepository.findById("1").get().getNUMBER()).isEqualTo("120");
    }

    private Version createVersion() {
        Version version = new Version();
        version.setVersion("20181000");
        return version;
    }
}
