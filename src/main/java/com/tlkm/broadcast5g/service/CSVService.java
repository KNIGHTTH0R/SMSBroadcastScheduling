package com.tlkm.broadcast5g.service;

import com.tlkm.broadcast5g.model.SMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

@Service
public class CSVService {


    Set<String> rawSMS;
    String directoryName;



    @Autowired
    private SMSSenderService smsSenderService;

    @Autowired
    private DataService dataService;


    public void processLoad(){

        Set<String> files = getFilesList();

        BufferedReader bufferedReader = null;

        for(String file : files){
            bufferedReader = loadFile(file);
            if(csvProcess(bufferedReader)){
                moveFile(file);
            };
        }

    }



    private BufferedReader loadFile(String fileName){
        return  null;
    }



    private boolean csvProcess(BufferedReader bufferedReader){

        String line;
        String split = ",";
        boolean isOpened = false;

        String msisdn = "";


        rawSMS = new HashSet<>();

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

        SMS smsResponse;

        smsResponse = smsSenderService.processSMS(msisdn);

        dataService.saveSMSData(smsResponse);

    }

    private Set<String> getFilesList(){
        File directory = new File(directoryName);

        Set<String> filesName = new HashSet<>();
        File[] fList = directory.listFiles();
        for (File file : fList){

            if(file.isFile())
                filesName.add(file.getName());

        }

        return filesName;
    }


    private int moveFile(String file){
        return 1;
    }


}
