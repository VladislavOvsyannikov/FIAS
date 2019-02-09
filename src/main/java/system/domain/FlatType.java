package system.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Информация по типам помещений
 */

@Entity
@Table(name = "flat_type")
@Data
@EqualsAndHashCode(of = "FLTYPEID")
public class FlatType {

    @Id
    @Column(name = "fltypeid", nullable = false)
    private int FLTYPEID;

    @Column(name = "name", nullable = false)
    private String NAME;
}
