package com.tlkm.broadcast5g.scheduling;

import com.tlkm.broadcast5g.service.OfferingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SMSOfferingScheduler {

    private static final Logger log = LoggerFactory.getLogger(SMSOfferingScheduler.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    @Autowired
    private OfferingService offeringService;

    @Scheduled(fixedDelay = 1800000)
    public void sendSMSSchedule() {
        log.info("Time start scheduler {}", dateFormat.format(new Date()));
        offeringService.processLoad2();
    }

}
