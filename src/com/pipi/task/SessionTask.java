package com.pipi.task;

import com.pipi.common.session.MySessionContext;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by yahto on 28/10/2017
 */
@Component
public class SessionTask extends MySessionContext {
    @Scheduled(cron = "0 */2 * * * ")
    public void clearSession() {
        sessionMap.clear();
    }
}
