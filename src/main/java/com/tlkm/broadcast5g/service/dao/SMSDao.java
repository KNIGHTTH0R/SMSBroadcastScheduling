package com.tlkm.broadcast5g.service.dao;

import com.tlkm.broadcast5g.model.SMSOffering;
import com.tlkm.broadcast5g.repository.database.SMSOfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Set;

@Component
public class SMSDao {

    @Autowired
    SMSOfferingRepository smsOfferingRepository;

    public void saveSMSData(SMSOffering smsOfferingResponse){
        smsOfferingRepository.save(smsOfferingResponse);
    }

    public boolean isPinExist(String pin){

        boolean isExist = false;

        Set<SMSOffering> smsOfferingList = smsOfferingRepository.findByPin(pin);

        for (SMSOffering smsOffering : smsOfferingList){
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
