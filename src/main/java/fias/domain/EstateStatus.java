package fias.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Информация по признакам владения
 */

@Entity
@Table(name = "estate_status")
@Data
@EqualsAndHashCode(of = "ESTSTATID")
public class EstateStatus {

    @Id
    @Column(name = "eststatid", nullable = false)
    private int ESTSTATID;

    @Column(name = "name", nullable = false)
    private String NAME;
}
