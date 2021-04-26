package cn.concox.app.cumstomFlow.Controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.concox.app.cumstomFlow.service.CustomTaskService;

@Component
@EnableScheduling
public class UpdateExpireFixedSchedule {

    Logger log = Logger.getLogger(UpdateExpireFixedSchedule.class);

    @Resource(name = "customTaskService")
    private CustomTaskService customTaskService;

    @Scheduled(fixedRate = 1000 * 60 * 5)
    public void updateExpireFixed() {
        try {
            customTaskService.updateExpireFixed();
        } catch (Exception e) {
            log.error("UpdateExpireFixedSchedule定时任务执行失败：", e);
        }
    }
}
