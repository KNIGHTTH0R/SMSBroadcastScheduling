package com.tlkm.broadcast5g.service;

import com.tlkm.broadcast5g.model.CSV;
import com.tlkm.broadcast5g.service.dao.CSVDao;
import com.tlkm.broadcast5g.service.dao.SMSDao;
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
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class OfferingService {


    private Set<String> msisdns;

    @Value("${csv.file.sms1.import}")
    private String directoryImport;

    @Value("${csv.file.log}")
    private String directoryLog;

    @Autowired
    private SMSDao SMSDao;

    @Autowired
    private SyncSample syncSample;

    @Autowired
    private OfferingThreadsService offeringThreadsService;

    @Autowired
    private ImportCSVService importCSVService;

    @Autowired
    private CSVDao csvDao;

    private Logger logger = LoggerFactory.getLogger(OfferingService.class);

    public void processLoad(){

        logger.debug("Process load csv");

        Set<String> files = getFilesList();

       // BufferedReader bufferedReader = null;
          for(String file : files){

            logger.debug("File "+file);
            BufferedReader bufferedReader = loadFile(file);
            if(csvProcess(bufferedReader, file)){

                moveFile(file);


            }
        }

        logger.debug("FINISHED PROCESS");

    }


    public void processLoad2(){

        logger.debug("Process load csv");

        Set<String> files = getFilesList();

        for(String file : files){

            logger.debug("File "+file);
            BufferedReader bufferedReader = loadFile(file);
          //  if(csvProcess(bufferedReader, file)){
            Set<CSV> csvs = saveDataToCSV(bufferedReader,file);

            try {
                bufferedReader.close();
            }catch (Exception ex){
                logger.error("buffereex "+ex.toString());
            }

            moveFile(file);

           Set<CSV> dataToBeSent = csvDao.getSMSData();
            sendSMSProcess(csvs);


        }

        logger.debug("FINISHED PROCESS");

    }


    private Set<CSV> saveDataToCSV(BufferedReader bufferedReader,String fileName){

        Set<CSV> csvs = importCSVService.mapToCSVs(bufferedReader,fileName);
        csvDao.bulkSaveCSVs(csvs);

        return csvs;

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


    private boolean csvProcess(BufferedReader bufferedReader,String fileName){

        String line;
        String splitTag = ",";
        boolean isOpened = false;

        String msisdn = "";
        String[] data = null;

        int idx = 0;
        int idxPhone = 3;

        msisdns = new HashSet<>();

     //   Set<CompletableFuture<String>> completableFutures = new HashSet<>();

      //  CompletableFuture<String> completableFuture;
        Collection<Future<String>> resultsAsync = new ArrayList<>();
        try{

            while((line = bufferedReader.readLine())!=null){
                data = line.split(splitTag);


                if(idx==0){
                    logger.debug("line idx 0 : "+line);

                    idxPhone = Arrays.asList(data).indexOf("NO_HP");
                    idx++;
                    continue;
                }

                logger.debug("line idx : "+idx+" "+line);


                msisdn = data[idxPhone];
                logger.debug("line msisdn :"+msisdn);

                resultsAsync.add(offeringThreadsService.smsProcessFuture(msisdn,fileName,line));

           //     offeringThreadsService.smsProcessFuture(msisdn,fileName,line);

                //  completableFuture = offeringThreadsService.smsProcessFuture(msisdn,fileName,line);
              //  completableFutures.add(completableFuture);
                idx++;


            }


         //   CompletableFuture.allOf(completableFutures.toArray(
           //         new CompletableFuture[completableFutures.size()]
           // )).join();


           resultsAsync.forEach(result -> {
                try {
                    result.get();
                } catch (InterruptedException | ExecutionException e) {
                    //handle thread error
                    logger.error("result async error due to "+e.toString());
                }
            });

            isOpened = true;

            try {
                bufferedReader.close();
            } catch (IOException e) {
                logger.error("buffered not close "+e.toString());
                e.printStackTrace();
            }

        }catch (Exception ex){
            logger.error("data error "+ex.toString());
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
            Files.move(sourcePath,destPath,StandardCopyOption.REPLACE_EXISTING);

            logger.debug("file "+source+" is moved to "+dest);


            return 1;
        } catch (IOException e) {
           logger.error("error move "+source+ ", because of "+e.toString());

            return 0;
        }

    }


    private void sendSMSProcess(Set<CSV> csvs){

       Collection<Future<String>> resultsAsync = new ArrayList<>();

       try{


           for (CSV csv : csvs){
               logger.debug("try to call async for "+csv.getNoHP());
               resultsAsync.add(offeringThreadsService.smsProcessFuture(csv));

           }

           resultsAsync.forEach(result -> {
                try {
                    result.get();
                } catch (InterruptedException | ExecutionException e) {
                    //handle thread error
                    logger.error("result async error due to "+e.toString());
                }
            });



        }catch (Exception ex){
            logger.error("data error "+ex.toString());

        }

    }



}
