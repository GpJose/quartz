package test.quartz.quartz.service;

import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import test.quartz.quartz.model.QuartzEntity;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

@Service
@Log4j2
@Configurable
public class QuartzEntityListener  {

    private Scheduler scheduler;

    @Autowired
    public QuartzEntityListener(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public QuartzEntityListener() {

    }

    @PostPersist
    @PostUpdate
    public void afterSave(QuartzEntity object) throws SchedulerException {
        JobDetail jobDetail = buildJobDetail(object);
        Trigger trigger = buildTrigger(object);

        if (scheduler.checkExists(jobDetail.getKey())) {
            scheduler.deleteJob(jobDetail.getKey());
        }

        scheduler.scheduleJob(jobDetail, trigger);
        log.info("Scheduled job with ID: {}", object.getId());
    }

    @PostRemove
    public void afterDelete(QuartzEntity object) throws SchedulerException {
        JobKey jobKey = new JobKey(object.getId().toString());
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
            log.info("Deleted job with ID: {}", object.getId());
        } else {
            log.warn("Attempted to delete non-existent job with ID: {}", object.getId());
        }
    }

    private JobDetail buildJobDetail(QuartzEntity object) {
        return JobBuilder.newJob(QuartzService.class)
                .withIdentity(object.getId().toString())
                .usingJobData("url", object.getUrl())
                .usingJobData("method", object.getMethod())
                .usingJobData("body", object.getBody())
                .usingJobData("headers", object.getHeaders())
                .build();
    }

    private Trigger buildTrigger(QuartzEntity object) {
        return TriggerBuilder.newTrigger()
                .withIdentity(object.getId().toString())
                .withSchedule(CronScheduleBuilder.cronSchedule(object.getCron()))
                .build();
    }
}
