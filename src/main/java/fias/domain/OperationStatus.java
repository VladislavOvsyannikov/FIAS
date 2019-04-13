package fias.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Информация по статусу действия
 */

@Entity
@Table(name = "operation_status")
@Data
@EqualsAndHashCode(of = "OPERSTATID")
public class OperationStatus {

    @Id
    @Column(name = "operstatid", nullable = false)
    private int OPERSTATID;

    @Column(name = "name", nullable = false)
    private String NAME;
}
