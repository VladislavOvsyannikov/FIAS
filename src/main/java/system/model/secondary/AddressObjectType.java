package system.model.secondary;

import javax.persistence.*;

/**
 * Информация по типам адресных объектов
 */

@Entity
@Table(name = "adress_object_type", schema = "fias", indexes = {
        @Index(columnList = "level, scname", name = "level_scname")})
public class AddressObjectType {

    @Id
    @Column(name = "kod_t_st", nullable = false)
    private String KOD_T_ST;

    //Уровень адресного объекта
    @Column(name = "level", nullable = false)
    private int LEVEL;

    //Полное наименование типа объекта
    @Column(name = "socrname", nullable = false)
    private String SOCRNAME;

    //Краткое наименование типа объекта
    @Column(name = "scname")
    private String SCNAME;


    public String getKOD_T_ST() {
        return KOD_T_ST;
    }

    public void setKOD_T_ST(String KOD_T_ST) {
        this.KOD_T_ST = KOD_T_ST;
    }

    public int getLEVEL() {
        return LEVEL;
    }

    public void setLEVEL(int LEVEL) {
        this.LEVEL = LEVEL;
    }

    public String getSOCRNAME() {
        return SOCRNAME;
    }

    public void setSOCRNAME(String SOCRNAME) {
        this.SOCRNAME = SOCRNAME;
    }

    public String getSCNAME() {
        return SCNAME;
    }

    public void setSCNAME(String SCNAME) {
        this.SCNAME = SCNAME;
    }
}
