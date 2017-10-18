package com.tlkm.broadcast5g.service;


import com.tlkm.broadcast5g.model.SMS;
import org.springframework.stereotype.Service;

@Service
public class SMSSenderService {



    public SMS processSMS(String msisdn){

        String optName = getOptName(msisdn);
        SMS smsResponse = null;
        String senderID = "1147";
        String content = "";


        if(!optName.equalsIgnoreCase("TSEL")){
            senderID = "TELKOM147";
        }

        content = generateContent(msisdn,senderID);

        smsResponse = sendSMS(msisdn,content,senderID);

        smsResponse.setOptName(optName);
        smsResponse.setSenderID(senderID);
        smsResponse.setContent(content);
        smsResponse.setSmsCount(smsCount(content));


        return smsResponse;
    }

    private String getOptName(String msisdn){

        String smsPrefixSplit = "";
        String optName = "Undefined";

        if(msisdn.startsWith("0")){
            smsPrefixSplit = msisdn.substring(1,3); //ex 08222
        }else if(msisdn.startsWith("62")){
            smsPrefixSplit = msisdn.substring(2,4); // 62822
        }else if(msisdn.startsWith("+62")){
            smsPrefixSplit = msisdn.substring(3,5); //+628
        }else {
            smsPrefixSplit = msisdn.substring(0,2);
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


    private SMS sendSMS(String msisdn, String content, String senderID){
        return null;
    }

    private String generateContent(String msisdn, String senderID){
        String content;

        if(senderID.equalsIgnoreCase("1147")){
            return "";
        }else{
            return "";
        }
    }


    private int smsCount(String content){
        int count = Math.round(content.length() / 160);
        return count;
    }



}
