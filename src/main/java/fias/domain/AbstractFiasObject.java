package fias.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractFiasObject {

    private String guid;

    private int ENDDATE;
}
