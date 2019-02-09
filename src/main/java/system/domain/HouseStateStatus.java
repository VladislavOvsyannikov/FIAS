package system.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Информация по статусу состояния домов
 */

@Entity
@Table(name = "house_state_status")
@Data
@EqualsAndHashCode(of = "HOUSESTID")
public class HouseStateStatus {

    @Id
    @Column(name = "housestid", nullable = false)
    private int HOUSESTID;

    @Column(name = "name", nullable = false)
    private String NAME;
}
