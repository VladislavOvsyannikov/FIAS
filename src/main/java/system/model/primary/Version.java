package system.model.primary;

import javax.persistence.*;

/**
 * История обновлений
 */

@Entity
@Table(name = "history_of_update", schema = "fias")
public class Version{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "version", nullable = false)
    private String version;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
