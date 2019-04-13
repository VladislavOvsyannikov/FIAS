package fias.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Log4j2
@Service
@PropertySource("classpath:fias.properties")
@RequiredArgsConstructor
public class AutoUpdate implements Job {

    private final Environment environment;
    private final FiasService fiasService;

    @Override
    public void execute(JobExecutionContext context) {
        boolean autoupdate = environment.getProperty("autoupdate").equals("true");
        if (autoupdate) {
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(context.getFireTime());
            log.info("Auto update started at {}", time);
            fiasService.installUpdates(false);
            String nextTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(context.getNextFireTime());
            log.info("Next auto update scheduled at {}", nextTime);
        }
    }
}
