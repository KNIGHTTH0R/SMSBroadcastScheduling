package com.tlkm.broadcast5g.service.threads;

import com.tlkm.broadcast5g.model.SMSOffering;
import com.tlkm.broadcast5g.service.dao.SMSDao;
import com.tlkm.broadcast5g.service.logic.SenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class OfferingThreadsService {

    @Autowired
    private SenderService senderService;

    @Autowired
    private SMSDao SMSDao;

    Logger logger = LoggerFactory.getLogger(OfferingThreadsService.class);

    @Async("offeringExecutor")
    public void smsProcess(String msisdn){
        logger.debug("###Start Processing with Thread name: " + Thread.currentThread().getName());

        SMSOffering smsOfferingResponse;

        smsOfferingResponse = senderService.processSMS(msisdn, SenderService.SMS_TYPE_1);

        logger.debug("sms result : "+smsOfferingResponse);
        SMSDao.saveSMSData(smsOfferingResponse);

    }

    @Async("offeringExecutor")
    public CompletableFuture<String> smsProcessFuture(String msisdn){
        logger.debug("###Start Processing with Thread name: " + Thread.currentThread().getName());

        SMSOffering smsOfferingResponse;

        smsOfferingResponse = senderService.processSMS(msisdn, SenderService.SMS_TYPE_1);

        logger.debug("sms result : "+smsOfferingResponse);
        SMSDao.saveSMSData(smsOfferingResponse);

        return CompletableFuture.completedFuture("finished");

    }
}
