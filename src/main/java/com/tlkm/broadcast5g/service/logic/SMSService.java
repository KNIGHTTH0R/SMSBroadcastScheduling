package com.tlkm.broadcast5g.service.logic;

import com.tlkm.broadcast5g.model.SMSOffering;
import com.tlkm.broadcast5g.repository.remote.GetRequester;
import com.tlkm.broadcast5g.utils.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SMSService {


    @Value("${sms.nadyne.url}")
    private String url="";

    @Value("${sms.nadyne.username}")
    private String username="";

    @Value("${sms.nadyne.password}")
    private String password="";

    @Autowired
    private GetRequester getRequester;


    private Logger logger = LoggerFactory.getLogger(SMSService.class);

    public String getOptName(String msisdn){

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


    public SMSOffering sendSMS(String msisdn, String content, String senderID){
        String responseReq = "";
        Map params = getParam(msisdn,content,senderID);

        int idxPrefix = 0;
        int idxPostfix = 0;

        String trxId = "";
        SMSOffering smsOffering = new SMSOffering();

        try{
            responseReq = getRequester.sendRequest(url,params);

            if(responseReq.contains("<trxid>")){
                idxPrefix = responseReq.indexOf("<trxid>") + 7;
                idxPostfix = responseReq.indexOf("</trxid>") - 1;

                trxId = responseReq.substring(idxPrefix,idxPostfix);
                smsOffering.setStatusId(Common.SUCCESS);
                smsOffering.setTrxId(trxId);

            }else{

                smsOffering.setStatusId(Common.FAILED);
                smsOffering.setStatusDesc(responseReq);
            }

        }catch (Exception ex){
            smsOffering.setStatusId(Common.FAILED);
            smsOffering.setStatusDesc(ex.toString());
        }

        return smsOffering;
    }

    public int smsCount(String content){
        int count = Math.round(content.length() / 160);
        return count;
    }

    public Map<String,String> getParam(String msisdn,String content,String senderID){

        Map params = new HashMap<String,String>();

        params.put("user",username);
        params.put("pwd",password);
        params.put("sender",senderID);
        params.put("msisdn",trimMsisdn(msisdn));
        params.put("message", content);

        return params;
    }

    private String trimMsisdn(String msisdn) {
        String result = msisdn;
        result = result.replaceAll("[^0-9]", "");

        if (result.startsWith("0")) {
            result = "62" + result.substring(1);
        }
        if (result.startsWith("8")) {
            result = "62" + result.substring(0);
        }
        return result;
    }

}
