package system.service;

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
import system.domain.Version;
import system.dto.AddrObjectDto;
import system.dto.HouseDto;
import system.dto.RoomDto;
import system.dto.SteadDto;
import system.repository.SteadRepository;
import system.repository.VersionRepository;

import javax.persistence.EntityManager;
import java.io.File;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.assertj.core.api.Java6Assertions.assertThat;

@Configuration
@WebAppConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = "system")
@EntityScan(basePackages = "system.domain")
@EnableJpaRepositories(basePackages = "system.repository")
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
    private EntityManager entityManager;
    private String guidPart = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    @BeforeClass
    @SneakyThrows
    public void installFiasComplete01() {
        File folder = ResourceUtils.getFile("classpath:complete01");
        this.installer.installDatabase(folder, "complete", "01", 10);
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
        assertThat((long) entityManager.createQuery("select count(flat) from FlatType flat").getSingleResult()).isEqualTo(10);
        assertThat((long) entityManager.createQuery("select count(house) from HouseStateStatus house").getSingleResult()).isEqualTo(43);
        assertThat((long) entityManager.createQuery("select count(room) from Room room").getSingleResult()).isEqualTo(8);
    }

    @Test
    public void getAddrObjectsStartListTest() {
        List<AddrObjectDto> addrObjects = fiasService.getAddrObjectsStartList();

        assertThat(addrObjects.size()).isEqualTo(1);
        assertThat(addrObjects.get(0).getFORMALNAME()).isEqualTo("Спб");
        assertThat(addrObjects.get(0).getLIVESTATUS()).isEqualTo(1);
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
                .searchObjectsByParameters("02-h" + guidPart, null, ParameterSearchType.ALL, false);

        assertThat(objects.size()).isEqualTo(1);

        objects = fiasService
                .searchObjectsByParameters("02-h", null, null, null);

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
        installer.installDatabase(folder, "delta", "02", 2);

        assertThat(steadRepository.count()).isEqualTo(6);
        assertThat(steadRepository.findById("1").get().getNUMBER()).isEqualTo("120");
    }

    private Version createVersion() {
        Version version = new Version();
        version.setVersion("20181000");
        return version;
    }
}
