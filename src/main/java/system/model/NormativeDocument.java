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
    @Column(name = "id", nullable = false)
    private int id;

    //Идентификатор нормативного документа
    @Column(name = "normdocid", nullable = false)
    private String NORMDOCID;

    //Наименование документа
    @Column(name = "docname")
    private String DOCNAME;

//    //Дата документа
//    @Column(name = "docdate")
//    private String DOCDATE;
//
//    //Номер документа
//    @Column(name = "docnum")
//    private String DOCNUM;

    //Тип документа
    @Column(name = "doctype")
    private int DOCTYPE;

//    //Идентификатор образа (внешний ключ)
//    @Column(name = "docimgid")
//    private String DOCIMGID;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNORMDOCID() {
        return NORMDOCID;
    }

    public void setNORMDOCID(String NORMDOCID) {
        this.NORMDOCID = NORMDOCID;
    }

    public String getDOCNAME() {
        return DOCNAME;
    }

    public void setDOCNAME(String DOCNAME) {
        this.DOCNAME = DOCNAME;
    }

    public int getDOCTYPE() {
        return DOCTYPE;
    }

    public void setDOCTYPE(int DOCTYPE) {
        this.DOCTYPE = DOCTYPE;
    }
}
