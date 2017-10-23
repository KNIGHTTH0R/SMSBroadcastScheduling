package com.tlkm.broadcast5g.scheduling;

import com.tlkm.broadcast5g.service.ExportCSVService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CSVGeneratorScheduler {

    private static final Logger log = LoggerFactory.getLogger(CSVGeneratorScheduler.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    @Autowired
    private ExportCSVService exportCSVService;

    @Scheduled(cron = "0 0 * * * *")
    public void generateReportCSV() {
        log.info("Time strat cron CSV generator {}", dateFormat.format(new Date()));

        exportCSVService.process();
    }
}
