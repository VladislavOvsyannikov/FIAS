package system.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddrObjectDto {

    private String AOGUID;

    private String PARENTGUID;

    private int LIVESTATUS;

    private String SHORTNAME;

    private String FORMALNAME;

    private String fullAddress;

    private String POSTALCODE;

    private String IFNSFL;

    private String IFNSUL;

    private String OKATO;

    private String OKTMO;
}
