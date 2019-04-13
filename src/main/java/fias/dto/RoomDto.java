package fias.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDto {

    private String ROOMGUID;

    private String HOUSEGUID;

    private int LIVESTATUS;

    private String FLATNUMBER;

    private String fullAddress;

    private String type;

    private String houseStateStatus;

    private String POSTALCODE;

    private String IFNSFL;

    private String IFNSUL;

    private String OKATO;

    private String OKTMO;

    private String CADNUM;
}
