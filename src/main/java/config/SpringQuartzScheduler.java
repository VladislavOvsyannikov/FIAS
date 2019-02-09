package config;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import system.service.AutoUpdate;

import java.sql.Time;

@Configuration
@ComponentScan("system")
public class SpringQuartzScheduler {

    private ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SchedulerFactoryBean scheduler(Trigger autoUpdateTrigger, JobDetail autoUpdateJobDetail) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));
        schedulerFactory.setJobFactory(springBeanJobFactory());
        schedulerFactory.setJobDetails(autoUpdateJobDetail);
        schedulerFactory.setTriggers(autoUpdateTrigger);
        schedulerFactory.setSchedulerName("autoUpdateScheduler");
        return schedulerFactory;
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        SpringBeanJobFactory jobFactory = new SpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SimpleTriggerFactoryBean autoUpdateTrigger(JobDetail autoUpdateJobDetail) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(autoUpdateJobDetail);
        trigger.setRepeatInterval(1000 * 60 * 60 * 24 * 3);
        trigger.setStartTime(Time.valueOf("04:00:00"));
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        trigger.setName("autoUpdateTrigger");
        return trigger;
    }

    @Bean
    public JobDetailFactoryBean autoUpdateJobDetail() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(AutoUpdate.class);
        jobDetailFactory.setName("autoUpdateJobDetail");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }
}
