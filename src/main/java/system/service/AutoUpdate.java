package system.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import system.dao.VersionDao;
import system.model.Version;

import java.text.SimpleDateFormat;

@Service
public class AutoUpdate implements Job {

    private static final Logger logger = LogManager.getLogger(AutoUpdate.class);

    private FiasService fiasService;

    @Autowired
    public void setFiasService(FiasService fiasService) {
        this.fiasService = fiasService;
    }

    @Override
    public void execute(JobExecutionContext context){
        if (FiasService.AUTO_UPDATE){
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(context.getFireTime());
            logger.info("Auto update started at {}", time);
            fiasService.installOneUpdate();
            String nextTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(context.getNextFireTime());
            logger.info("Next auto update scheduled at {}", nextTime);
        }
    }
}
