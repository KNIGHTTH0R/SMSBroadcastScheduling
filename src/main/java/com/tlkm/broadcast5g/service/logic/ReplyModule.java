package com.tlkm.broadcast5g.service.logic;

import com.tlkm.broadcast5g.model.CSV;
import com.tlkm.broadcast5g.service.dao.LogReplyDao;
import com.tlkm.broadcast5g.service.dao.SMSDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;


@Service
public class ReplyModule {


    @Autowired
    SMSDao smsDao;

    @Autowired
    AuthModule authModule;

    @Autowired
    SMSModule smsModule;

    @Autowired
    LogReplyDao logReplyDao;


    public String replyProcess(String apiKey,
                               String content,
                               String encryptNo,
                               String optId){

        logReplyDao.saveLog(encryptNo,content,optId);


        if(!authModule.auth(apiKey)){
            return "";
        }

        return getReplyContent(content, encryptNo);
    }

    private String getReplyContent(String content, String encryptNo) {
        Date date = new Date();

        String replyData = "";


        String[] splitContent = content.split(" ");

        try{
            String answer = splitContent[0];
            String pin = splitContent[1];

            if(!answer.equalsIgnoreCase("y")){
                replyData =  smsModule.generateContent(SMSModule.SMS_FAILED_FORMAT);
                return replyData;
            }

            CSV csv = smsDao.getSMSbyPIN(pin);

            if(csv==null || csv.getPin().equalsIgnoreCase("") ){
                replyData = smsModule.generateContent(SMSModule.SMS_FAILED_PIN);

             //   csv.setReplyContent(content);
              //  csv.setReplyDate(date);
               // csv.setEncrtypNo(encryptNo);
               // csv.setReplyStatus(SMSModule.SMS_FAILED_PIN);
               // csv.setReplyStatusDesc("SMS_FAILED_PIN");

            }else{

                csv.setReplyContent(content);
                csv.setReplyDate(date);
                csv.setEncrtypNo(encryptNo);
                csv.setReplyStatus(1);
                csv.setReplyStatusDesc("SMS REPLY SUCCESS");

                replyData = smsModule.generateContent(SMSModule.SMS_REPLY);
                smsDao.saveSMSData(csv);

            }






        }catch (Exception ex){

            replyData = smsModule.generateContent(SMSModule.SMS_FAILED_FORMAT);
        }

        return replyData;
    }




}
