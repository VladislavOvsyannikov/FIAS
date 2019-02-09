package system.service;

import lombok.Getter;
import lombok.Setter;
import system.dto.AddrObjectDto;

import java.util.List;

@Getter
@Setter
public class CustomPair {

    private List<AddrObjectDto> old;
    private AddrObjectDto actual;

    CustomPair(List<AddrObjectDto> old, AddrObjectDto actual) {
        this.old = old;
        this.actual = actual;
    }
}
