package com.tlkm.broadcast5g.service.logic;

import com.tlkm.broadcast5g.model.SMSOffering;
import com.tlkm.broadcast5g.repository.database.SMSOfferingRepository;
import com.tlkm.broadcast5g.service.dao.SMSDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyModule {


    @Autowired
    SMSDao smsDao;

    public String replyProcess(String apiKey,String pin){

        String response = "";
        SMSOffering smsOffering = smsDao.getSMSbyPIN(pin);


        return "";
    }





    public String generateContent(String msisdn, String senderID,String pin){
        String content;

        content = "";

        return content;
    }




}