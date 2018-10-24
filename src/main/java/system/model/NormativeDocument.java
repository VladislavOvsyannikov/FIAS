package system.model;

import javax.persistence.*;

/**
 * Информация по сведениям по нормативным документам,
 * являющимся основанием присвоения адресному элементу наименования
 */

@Entity
@Table(name = "normative_document", schema = "fias")
public class NormativeDocument {

    @Id
    @Column(name = "normdocid", nullable = false, unique = true, length = 32)
    private String NORMDOCID;

    //Тип документа
    @Column(name = "doctype", length = 3)
    private int DOCTYPE;

//    //Номер документа
//    @Column(name = "docnum" , length = 20)
//    private String DOCNUM;
//
//    //Наименование документа
//    @Column(name = "docname", length = 1000)
//    private String DOCNAME;

//    //Идентификатор образа (внешний ключ)
//    @Column(name = "docimgid")
//    private String DOCIMGID;

//    //Дата документа
//    @Column(name = "docdate")
//    private String DOCDATE;


    public String getNORMDOCID() {
        return NORMDOCID;
    }

    public void setNORMDOCID(String NORMDOCID) {
        this.NORMDOCID = NORMDOCID;
    }

    public int getDOCTYPE() {
        return DOCTYPE;
    }

    public void setDOCTYPE(int DOCTYPE) {
        this.DOCTYPE = DOCTYPE;
    }
}
