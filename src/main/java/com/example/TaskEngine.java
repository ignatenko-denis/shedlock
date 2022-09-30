package com.example;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class TaskEngine {
    /**
     * The @SchedulerLock annotation has several purposes.
     * First, only annotated methods are locked, the library ignores all others scheduled tasks.
     * You also have to specify the name for the lock.
     * Only one task with the same name can be executed at the same time.
     */
    @Scheduled(cron = "${app.task-interval}")
    @SchedulerLock(name = "long_task",
            lockAtMostFor = "${app.lock-at-most-for}",
            lockAtLeastFor = "${app.lock-at-least-for}")
    public void longTask() throws InterruptedException {
        // To assert that the lock is held (prevents misconfiguration errors)
        LockAssert.assertLocked();

        log.info("long task started at " + LocalDateTime.now());
        Thread.sleep(30 * 1000);
        log.info("long task finished at " + LocalDateTime.now());
    }
}
