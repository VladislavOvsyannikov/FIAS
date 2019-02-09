package system.service;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class NameSearchTypeTest {

    @Test
    public void getQueryPartTest() {
        assertEquals(NameSearchType.ALL.getQueryPart(), "");
        assertTrue(NameSearchType.REPUBLIC.getQueryPart().contains("a.SHORTNAME='Респ'"));
        assertTrue(NameSearchType.AUTONOMOUS_DISTRICT.getQueryPart().contains("a.SHORTNAME='АО'"));
        assertTrue(NameSearchType.CITY.getQueryPart().contains("a.SHORTNAME='г'"));
        assertTrue(NameSearchType.REGION.getQueryPart().contains("a.SHORTNAME='обл'"));
        assertTrue(NameSearchType.DISTRICT.getQueryPart().contains("a.SHORTNAME='р-н'"));
        assertTrue(NameSearchType.EDGE.getQueryPart().contains("a.SHORTNAME='край'"));
        assertTrue(NameSearchType.AUTONOMOUS_REGION.getQueryPart().contains("a.SHORTNAME='Аобл'"));
        assertTrue(NameSearchType.STREET.getQueryPart().contains("a.SHORTNAME='ул'"));
        assertTrue(NameSearchType.AVENUE.getQueryPart().contains("a.SHORTNAME='пр-кт'"));
        assertTrue(NameSearchType.VILLAGE.getQueryPart().contains("a.SHORTNAME='с'"));
        assertTrue(NameSearchType.HAMLET.getQueryPart().contains("a.SHORTNAME='д'"));
        assertTrue(NameSearchType.SETTLEMENT.getQueryPart().contains("a.SHORTNAME='п'"));
        assertTrue(NameSearchType.URBAN_TYPE_SETTLEMENT.getQueryPart().contains("a.SHORTNAME='пгт'"));
        assertEquals(NameSearchType.UNSET.getQueryPart(), "");
    }
}