package com.tlkm.broadcast5g.service;

import com.tlkm.broadcast5g.service.dao.SMSDao;
import com.tlkm.broadcast5g.service.logic.SenderService;
import com.tlkm.broadcast5g.service.logic.SyncSample;
import com.tlkm.broadcast5g.service.threads.OfferingThreadsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class OfferingService {


    private Set<String> msisdns;

    @Value("${csv.file.sms1.import}")
    private String directoryImport;

    @Value("${csv.file.log}")
    private String directoryLog;

    @Autowired
    private SenderService smsSenderService;

    @Autowired
    private SMSDao SMSDao;

    @Autowired
    private SyncSample syncSample;

    @Autowired
    private OfferingThreadsService offeringThreadsService;

    private Logger logger = LoggerFactory.getLogger(OfferingService.class);

    public void processLoad(){

        logger.debug("Process load csv");

        Set<String> files = getFilesList();

       // BufferedReader bufferedReader = null;

        for(String file : files){
            BufferedReader bufferedReader = loadFile(file);
            if(csvProcess(bufferedReader)){

                moveFile(file);


            }
        }

    }


    private BufferedReader loadFile(String fileName){

        String filePath = directoryImport + fileName;
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(filePath));

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

        Set<CompletableFuture<String>> completableFutures = new HashSet<>();


        try{

            while((line = bufferedReader.readLine())!=null){

                logger.debug("line : "+line);

                msisdn = line;

               // offeringThreadsService.smsProcess(msisdn);
                completableFutures.add(offeringThreadsService.smsProcessFuture(msisdn));
            }


            CompletableFuture.allOf(completableFutures.toArray(
                    new CompletableFuture[completableFutures.size()]
            )).join();

            isOpened = true;

            try {
                bufferedReader.close();
            } catch (IOException e) {
                logger.error("buffered not close "+e.toString());
                e.printStackTrace();
            }

        }catch (Exception ex){
            isOpened = false;
        }

        return isOpened;

    }


    private Set<String> getFilesList(){
        logger.debug("start obtain files in directory : "+ directoryImport);

        File directory = new File(directoryImport);

        Set<String> filesName = new HashSet<>();
        File[] fList = directory.listFiles();

        String fileName = "";

        if(fList!=null){
            logger.debug("File List available");

            for (File file : fList){


                fileName = file.getName();

                if(file.isFile() && fileName.endsWith("csv")){
                    logger.debug("CSV File "+file.getName());

                    filesName.add(file.getName());
                }

            }
        }

        return filesName;
    }


    private int moveFile(String file){

        String source = directoryImport + file;
        String dest = directoryLog + file;

        Path sourcePath = Paths.get(source);
        Path destPath = Paths.get(dest);

        try {
            Files.move(sourcePath,destPath);

            logger.debug("file "+source+" is moved to "+dest);


            return 1;
        } catch (IOException e) {
           logger.error("error move "+source+ ", because of "+e.toString());

            return 0;
        }

    }




}
