package fias.service;

import fias.dto.AddrObjectDto;
import lombok.Getter;
import lombok.Setter;

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
