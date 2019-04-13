package fias.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Информация по статусу центра
 */

@Entity
@Table(name = "center_status")
@Data
@EqualsAndHashCode(of = "CENTERSTID")
public class CenterStatus {

    @Id
    @Column(name = "centerstid", nullable = false)
    private int CENTERSTID;

    @Column(name = "name", nullable = false)
    private String NAME;
}
