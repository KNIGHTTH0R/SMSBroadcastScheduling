package com.tlkm.broadcast5g.service.logic;

import com.tlkm.broadcast5g.model.CSV;
import com.tlkm.broadcast5g.model.SMS;
import com.tlkm.broadcast5g.model.SMSOffering;
import com.tlkm.broadcast5g.model.SMSReply;
import com.tlkm.broadcast5g.repository.remote.GetRequester;
import com.tlkm.broadcast5g.utils.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SMSModule {


    @Value("${sms.nadyne.url}")
    private String url="";

    @Value("${sms.nadyne.username}")
    private String username="";

    @Value("${sms.nadyne.password}")
    private String password="";

    @Autowired
    private GetRequester getRequester;

    public static final int SMS_TYPE_1 = 1;
    public static final int SMS_TYPE_3 = 3;
    public static final int SMS_REPLY = 2;
    public static final int SMS_FAILED_FORMAT = 4;
    public static final int SMS_FAILED_PIN = 5;


    @Autowired
    private com.tlkm.broadcast5g.service.dao.SMSDao SMSDao;

    @Autowired
    private PINModule pinModule;

    @Autowired
    private Environment environment;



    private Logger logger = LoggerFactory.getLogger(SMSModule.class);

    public String getOptNameOld(String msisdn){

        String smsPrefixSplit = "";
        String optName = "Undefined";

        if(msisdn.startsWith("0")){
            smsPrefixSplit = msisdn.substring(1,3); //ex 08222
        }else if(msisdn.startsWith("62")){
            smsPrefixSplit = msisdn.substring(2,4); // 62822
        }else if(msisdn.startsWith("+62")){
            smsPrefixSplit = msisdn.substring(3,5); //+628
        }else {


       //     smsPrefixSplit = msisdn.substring(0,2);
        }


        switch (smsPrefixSplit){
            case "812" :
                optName = "TSEL";
                break;
            case "813" :
                optName = "TSEL";
                break;
            case "857" :
                optName = "ISAT";
                break;
            default:
                optName = "Unknown";

        }

        return optName;

    }

    private String getOptName(String msisdn){
        String optName = "NONTSEL";
        String recipient = "";

        if (msisdn.startsWith("62")) {
            recipient = "0"+msisdn.substring(1);
        }else{
            recipient = msisdn;
        }

        if(msisdn.startsWith("8")){
            recipient = "0"+msisdn;
        }

        if(recipient.startsWith("0811") | recipient.startsWith("0812") | recipient.startsWith("0813")){
            optName = "TSEL";
        }


        if(recipient.startsWith("0821") | recipient.startsWith("0822") | recipient.startsWith("0823")){
            optName = "TSEL";
        }


        if(recipient.startsWith("0851") | recipient.startsWith("0852") | recipient.startsWith("0853")){
            optName = "TSEL";
        }

        return optName;
    }
    public SMS sendSMS(String msisdn, String content, String senderID, int type){
        String responseReq = "";
        Map params = getParam(msisdn,content,senderID);

        int idxPrefix = 0;
        int idxPostfix = 0;

        String trxId = "";
        SMS sms = null;

        if(type ==SMS_TYPE_1){
            sms = new SMSOffering();
        }else{
            sms = new SMSReply();
        }

        try{
            responseReq = getRequester.sendRequest(url,params);
            String responseLower = responseReq.toLowerCase();

            if(responseLower.contains("<trxid>")){

                idxPrefix = responseLower.indexOf("<trxid>") + 7;
                idxPostfix = responseLower.indexOf("</trxid>") - 1;

                trxId = responseReq.substring(idxPrefix,idxPostfix);

                logger.debug("TRX ID "+trxId);

                if(!trxId.equalsIgnoreCase("0") && !trxId.trim().equalsIgnoreCase("")){
                    sms.setStatusId(Common.SUCCESS);
                    sms.setTrxId(trxId);
                    sms.setStatusDesc("SENT");
                  //  sms.setStatusDesc(responseReq);
                }else{
                    sms.setStatusId(Common.FAILED);
                    sms.setTrxId(trxId);
                 //   sms.setStatusDesc("SENT");
                    sms.setStatusDesc(responseReq);
                }


            }else{

                sms.setStatusId(Common.FAILED);
                sms.setStatusDesc(responseReq);

                sms.setStatusDesc("FAILED");
            }

        }catch (Exception ex){
            sms.setStatusId(Common.FAILED);
            sms.setStatusDesc(ex.toString());
        }

        sms.setSmsCount(smsCount(content));

       // Date date = new Date();
       // sms.setSentDate(date);

        return sms;
    }

    public int smsCount(String content){
      //  int count = Math.round(content.length() / 160);
      //  return count;

        int sLength = content.length();
        double chunk = 160;
        double bagi = sLength/chunk;
        int token = (int)Math.ceil(bagi);

        return token;
    }

    public Map<String,String> getParam(String msisdn,String content,String senderID){

        Map params = new HashMap<String,String>();

        params.put("user",username);
        params.put("pwd",password);
        params.put("sender",senderID);
        params.put("msisdn", Common.trimMsisdn(msisdn));
        params.put("message", content);

        return params;
    }



    public SMS processSMS(String msisdn, int smsType){

        String optName = getOptName(msisdn);
        SMS sms = null;
        String senderID = "1147";
        String content = "";

        if(!optName.equalsIgnoreCase("TSEL")){
            senderID = "TELKOM147";
        }

        String pin = "";
        if(smsType == SMSModule.SMS_TYPE_1){
            pin = pinModule.generatePIN(msisdn);
            content = generateContent(senderID,pin);
        }
        else{
            content = generateContent(senderID,smsType);

        }

        logger.debug("sms to send "+content);

        sms = sendSMS(msisdn,content,senderID, smsType);

        sms.setOptName(optName);
        sms.setSenderID(senderID);
        sms.setContent(content);
        sms.setSmsCount(smsCount(content));
        sms.setPin(pin);


        return sms;
    }

    public CSV processCSV(String msisdn, int smsType){

        String optName = getOptName(msisdn);
        CSV csv = null;
        SMS sms = null;
        String senderID = "1147";
        String content = "";

        if(!optName.equalsIgnoreCase("TSEL")){
            senderID = "1147";
        }

        String pin = "";
        if(smsType == SMSModule.SMS_TYPE_1){
            pin = pinModule.generatePIN(msisdn);
            content = generateContent(senderID,pin);
        }
        else{
            content = generateContent(senderID,smsType);

        }

      //  logger.debug("sms to send "+content);

        sms = sendSMS(msisdn,content,senderID, smsType);

        sms.setOptName(optName);
        sms.setSenderID(senderID);
        sms.setContent(content);
        sms.setSmsCount(smsCount(content));
        sms.setPin(pin);

        csv = new CSV();
        csv.setStatusId(sms.getStatusId());
        csv.setStatusDesc(sms.getStatusDesc());
        csv.setTrxId(sms.getTrxId());
        csv.setOptName(sms.getOptName());
        csv.setSenderID(sms.getSenderID());
        csv.setContent(sms.getContent());
        csv.setSmsCount(sms.getSmsCount());
        csv.setPin(pin);

        return csv;
    }

    public CSV processCSV(CSV csv, int smsType){

        logger.debug("process csv "+csv.getNoHP());
        String optName = getOptName(csv.getNoHP());
        Date date = new Date();
        SMS sms = null;
        String senderID = "1147";
        String content = "";

        if(!optName.equalsIgnoreCase("TSEL")){
            senderID = "TELKOM147";
        }

        String pin = "";
        if(smsType == SMSModule.SMS_TYPE_1){
            pin = pinModule.generatePIN(csv.getNoHP());
            content = generateContent(senderID,pin);
        }
        else{
            content = generateContent(senderID,smsType);

        }

        logger.debug("sms to send "+content);

        sms = sendSMS(csv.getNoHP(),content,senderID, smsType);

        sms.setOptName(optName);
        sms.setSenderID(senderID);
        sms.setContent(content);
        sms.setSmsCount(smsCount(content));
        sms.setPin(pin);

        csv.setStatusId(sms.getStatusId());
        csv.setStatusDesc(sms.getStatusDesc());
        csv.setTrxId(sms.getTrxId());
        csv.setOptName(sms.getOptName());
        csv.setSenderID(sms.getSenderID());
        csv.setContent(sms.getContent());
        csv.setSmsCount(sms.getSmsCount());
        csv.setPin(pin);
        csv.setSentDate(date);

        return csv;
    }


    public String generateContent(String senderID,String pin){
        String content;

     //   logger.debug("SMS TYPE 1");

        if(senderID.equalsIgnoreCase("1147")){
            content = environment.getProperty("smsOffering.content.1147");
        }else{
            content = environment.getProperty("smsOffering.content.telkom147");
        }

        content = content.replace("<PIN>",pin);

        return content;
    }


    public String generateContent(String senderID,int smsType){
        String content = "";

        logger.debug("SMS TYPE 1");

       if(smsType!=SMS_TYPE_1){
           content = generateContent(smsType);
       }

      //  content = content.replace("<PIN>",pin);

        return content;
    }

    public String generateContent(int smsType){
        String content = "";

        switch (smsType){

            case SMS_TYPE_3 :
                content = environment.getProperty("smsInformation.content");
                break;
            case SMS_REPLY :
                content = environment.getProperty("sms.content.reply");
                break;
            case SMS_FAILED_FORMAT :
                content = environment.getProperty("sms.content.falsereply");
                break;
            case SMS_FAILED_PIN :
                content = environment.getProperty("sms.content.falsepin");
                break;

        }


        return content;
    }





}
