package system.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Типы помещений
 */

@Entity
@Table(name = "room_type")
@Data
@EqualsAndHashCode(of = "RMTYPEID")
public class RoomType {

    @Id
    @Column(name = "rmtypeid", nullable = false)
    private int RMTYPEID;

    @Column(name = "name", nullable = false)
    private String NAME;
}
