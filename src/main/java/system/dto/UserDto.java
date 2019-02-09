package system.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private int id;

    private String name;

    private String role;

    private Boolean isEnable;
}
