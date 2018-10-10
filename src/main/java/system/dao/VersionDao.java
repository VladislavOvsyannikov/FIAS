package system.dao;

import org.springframework.stereotype.Repository;
import system.model.Version;

@Repository
public class VersionDao extends GenericDao<Version>{

    public String getCurrentVersion() {
        Version version = getEntity(
                "select * from history_of_update order by id desc limit 1", Version.class);
        if (version!=null) return version.getVersion();
        return null;
    }
}
