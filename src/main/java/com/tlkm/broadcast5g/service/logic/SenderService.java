package com.tlkm.broadcast5g.service.logic;


import com.tlkm.broadcast5g.model.SMSOffering;
import com.tlkm.broadcast5g.service.dao.SMSDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class SenderService {


    @Autowired
    private SMSService smsService;

    @Autowired
    private SMSDao SMSDao;

    @Autowired
    private PINService pinService;

    @Autowired
    private Environment environment;


    public static int SMS_TYPE_1 = 1;
    public static int SMS_TYPE_3 = 3;

    Logger logger = LoggerFactory.getLogger(SenderService.class);



    public SMSOffering processSMS(String msisdn, int smsType){

        String optName = smsService.getOptName(msisdn);
        SMSOffering smsOfferingResponse = null;
        String senderID = "1147";
        String content = "";

        if(!optName.equalsIgnoreCase("TSEL")){
            senderID = "TELKOM147";
        }

        String pin = pinService.generatePIN(msisdn);

        if(smsType == SMS_TYPE_1)
            content = generateContent(msisdn,senderID,pin);
        else
            content = generateContent(msisdn,smsType);



        logger.debug("sms to send "+content);

        smsOfferingResponse = smsService.sendSMS(msisdn,content,senderID);

        smsOfferingResponse.setOptName(optName);
        smsOfferingResponse.setSenderID(senderID);
        smsOfferingResponse.setContent(content);
        smsOfferingResponse.setSmsCount(smsService.smsCount(content));
        smsOfferingResponse.setPin(pin);


        return smsOfferingResponse;
    }


    public String generateContent(String msisdn, String senderID,String pin){
        String content;

        logger.debug("SMS TYPE 1");

        if(senderID.equalsIgnoreCase("1147")){
            content = environment.getProperty("smsOffering.content.1147");
        }else{
            content = environment.getProperty("smsOffering.content.telkom147");;
        }

        content.replace("<PIN>",pin);

        return content;
    }

    private String generateContent(String msisdn, int smsType){
        String content;

        content = "";
        return content;
    }




}
