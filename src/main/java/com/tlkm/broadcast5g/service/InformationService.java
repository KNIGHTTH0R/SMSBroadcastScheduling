package com.tlkm.broadcast5g.service;

import com.tlkm.broadcast5g.model.SMSOffering;
import com.tlkm.broadcast5g.service.dao.SMSDao;
import com.tlkm.broadcast5g.service.logic.SenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

@Service
public class InformationService {

    Set<String> msisdns;

    @Value("${csv.file.sms3.import}")
    private String directoryName;

    @Autowired
    private SenderService smsSenderService;

    @Autowired
    private SMSDao SMSDao;

    Logger logger = LoggerFactory.getLogger(InformationService.class);

    public void processLoad(){

        Set<String> files = getFilesList();

        BufferedReader bufferedReader = null;

        for(String file : files){
            bufferedReader = loadFile(file);
            if(csvProcess(bufferedReader)){
                moveFile(file);
            }
        }

    }

    private BufferedReader loadFile(String fileName){

        String filePath = directoryName + fileName;
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(fileName));

        }catch (Exception ex){
            br = null;
        }
        return  br;
    }



    private boolean csvProcess(BufferedReader bufferedReader){

        String line;
        String split = ",";
        boolean isOpened = false;

        String msisdn = "";


        msisdns = new HashSet<>();

        try{

            while((line = bufferedReader.readLine())!=null){

                msisdn = line;
                smsProcess(msisdn);

            }
            isOpened = true;

        }catch (Exception ex){
            isOpened = false;
        }

        return isOpened;

    }


    private void smsProcess(String msisdn){

        SMSOffering smsOfferingResponse;

        smsOfferingResponse = smsSenderService.processSMS(msisdn, SenderService.SMS_TYPE_3);

        SMSDao.saveSMSData(smsOfferingResponse);

    }

    private Set<String> getFilesList(){
        File directory = new File(directoryName);

        Set<String> filesName = new HashSet<>();
        File[] fList = directory.listFiles();

        if(fList!=null){
            logger.debug("File List available");

            for (File file : fList){

                logger.debug("File "+file.getName());


                if(file.isFile()){
                    filesName.add(file.getName());
                }

            }
        }


        return filesName;
    }


    private int moveFile(String file){
        return 1;
    }

}
