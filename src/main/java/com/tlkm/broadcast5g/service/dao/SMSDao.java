package com.tlkm.broadcast5g.service.dao;

import com.tlkm.broadcast5g.model.CSV;
import com.tlkm.broadcast5g.model.SMS;
import com.tlkm.broadcast5g.model.SMSOffering;
import com.tlkm.broadcast5g.repository.database.CSVRepository;
import com.tlkm.broadcast5g.repository.database.SMSOfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Set;

@Component
public class SMSDao {

    @Autowired
    SMSOfferingRepository smsOfferingRepository;

    @Autowired
    CSVRepository csvRepository;

    public void saveSMSData(SMS sms){

        if(sms instanceof SMSOffering){
            smsOfferingRepository.save((SMSOffering)sms);
        }
        else if(sms instanceof CSV){
            csvRepository.save((CSV)sms);
        }

    }

    public boolean isPinExist(String pin){

        boolean isExist = false;

        Set<CSV> csvs = csvRepository.findByPin(pin);

        for (CSV csv : csvs){
            isExist = true;
        }

        return isExist;

    }


    public SMSOffering getSMSbyPIN(String pin){

        SMSOffering smsOffering = new SMSOffering();

        Set<SMSOffering> smsOfferingList = smsOfferingRepository.findByPin(pin);

        for (SMSOffering smsOfferingTemp : smsOfferingList){
            smsOffering = smsOfferingTemp;
        }

        return smsOffering;

    }





}
