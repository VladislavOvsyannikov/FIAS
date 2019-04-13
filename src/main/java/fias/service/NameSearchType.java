package fias.service;

public enum NameSearchType {
    ALL,
    CITY,
    REGION,
    DISTRICT,
    EDGE,
    REPUBLIC,
    AUTONOMOUS_DISTRICT,
    AUTONOMOUS_REGION,
    STREET,
    AVENUE,
    VILLAGE,
    HAMLET,
    SETTLEMENT,
    URBAN_TYPE_SETTLEMENT;

    public String getQueryPart() {
        switch (this) {
            case ALL:
                return "";
            case CITY:
                return " and (a.SHORTNAME='г' or a.SHORTNAME='г.')";
            case REGION:
                return " and (a.SHORTNAME='обл' or a.SHORTNAME='обл.')";
            case DISTRICT:
                return " and a.SHORTNAME='р-н'";
            case EDGE:
                return " and a.SHORTNAME='край'";
            case REPUBLIC:
                return " and (a.SHORTNAME='Респ' or a.SHORTNAME='Респ.')";
            case AUTONOMOUS_DISTRICT:
                return " and a.SHORTNAME='АО'";
            case AUTONOMOUS_REGION:
                return " and a.SHORTNAME='Аобл'";
            case STREET:
                return " and (a.SHORTNAME='ул' or a.SHORTNAME='ул.')";
            case AVENUE:
                return " and a.SHORTNAME='пр-кт'";
            case VILLAGE:
                return " and (a.SHORTNAME='с' or a.SHORTNAME='с.')";
            case HAMLET:
                return " and (a.SHORTNAME='д' or a.SHORTNAME='д.')";
            case SETTLEMENT:
                return " and (a.SHORTNAME='п' or a.SHORTNAME='п.')";
            case URBAN_TYPE_SETTLEMENT:
                return " and (a.SHORTNAME='пгт' or a.SHORTNAME='пгт.')";
            default:
                return "";
        }
    }
}
