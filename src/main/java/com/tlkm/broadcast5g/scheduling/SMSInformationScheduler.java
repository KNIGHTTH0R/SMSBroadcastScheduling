package com.tlkm.broadcast5g.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SMSInformationScheduler {

    private static final Logger log = LoggerFactory.getLogger(SMSInformationScheduler.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");


    @Scheduled(fixedDelay = 60000)
    public void smsConfirmation() {
        log.info("Time start scheduler {}", dateFormat.format(new Date()));

    }

}
