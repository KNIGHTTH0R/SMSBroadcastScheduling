package com.tlkm.broadcast5g.service.threads;

import com.tlkm.broadcast5g.model.CSV;
import com.tlkm.broadcast5g.service.dao.SMSDao;
import com.tlkm.broadcast5g.service.logic.SMSModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class OfferingThreadsService {

    @Autowired
    private SMSDao smsDao;

    @Autowired
    private SMSModule smsModule;

    Logger logger = LoggerFactory.getLogger(OfferingThreadsService.class);
/*
    @Async("offeringExecutor")
    public void smsProcess(String msisdn){
        logger.debug("###Start Processing with Thread name: " + Thread.currentThread().getName());

        SMS sms;

        sms = smsModule.processSMS(msisdn, SMSModule.SMS_TYPE_1);

        logger.debug("sms result : "+sms);
        SMSDao.saveSMSData(sms);

    }
*/


    @Async("offeringExecutor")
    public Future<String> smsProcessFuture(String msisdn, String fileName, String line){
        logger.debug("###Start Processing with Thread name: " + Thread.currentThread().getName());

        CSV csv;

        String[] data = line.split(",");

        csv = smsModule.processCSV(msisdn, SMSModule.SMS_TYPE_1);

        csv.setFileName(fileName);
        csv.setNcli(data[0]);
        csv.setNdPots(data[1]);
        csv.setNamaPelanggan(data[2]);
        csv.setNoHP(data[3]);
        csv.setEmail(data[4]);
        csv.setNamaPemilik(data[5]);
        csv.setAlamat(data[6]);
        csv.setKota(data[7]);
        csv.setRegional(data[8]);


        smsDao.saveSMSData(csv);

        //return CompletableFuture.completedFuture("finished");
        return new AsyncResult<String>("return value");

    }


/*
    @Async
    public void smsProcessFuture(String msisdn, String fileName, String line){
        logger.debug("###Start Processing with Thread name: " + Thread.currentThread().getName());

        CSV csv;

        String[] data = line.split(",");

        csv = smsModule.processCSV(msisdn, SMSModule.SMS_TYPE_1);

        csv.setFileName(fileName);
        csv.setNcli(data[0]);
        csv.setNdPots(data[1]);
        csv.setNamaPelanggan(data[2]);
        csv.setNoHP(data[3]);
        csv.setEmail(data[4]);
        csv.setNamaPemilik(data[5]);
        csv.setAlamat(data[6]);
        csv.setKota(data[7]);
        csv.setRegional(data[8]);


        SMSDao.saveSMSData(csv);


    }
*/

    @Async("offeringExecutor")
    public Future<String> smsProcessFuture(CSV csv){
        logger.debug("###Start Processing with Thread name: " + Thread.currentThread().getName());


        csv = smsModule.processCSV(csv, SMSModule.SMS_TYPE_1);

        smsDao.saveSMSData(csv);

        //return CompletableFuture.completedFuture("finished");
        return new AsyncResult<String>("return value");

    }

}
