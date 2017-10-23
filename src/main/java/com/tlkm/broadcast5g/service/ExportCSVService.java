package com.tlkm.broadcast5g.service;

import com.tlkm.broadcast5g.model.CSV;
import com.tlkm.broadcast5g.model.UploadHistory;
import com.tlkm.broadcast5g.repository.database.CSVRepository;
import com.tlkm.broadcast5g.repository.database.UploadHistoryRepository;
import com.tlkm.broadcast5g.utils.CSVUtils;
import com.tlkm.broadcast5g.utils.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExportCSVService {

    @Autowired
    UploadHistoryRepository uploadHistoryRepository;

    @Autowired
    CSVRepository csvRepository;


    String exportDirecotry;

    Logger logger = LoggerFactory.getLogger(ExportCSVService.class);

    public void process(){
        Set<UploadHistory> uploadHistories = getByUploadData();
        Set<String> fileNames = getFileNames(uploadHistories);

        logger.debug("file names : "+fileNames.toString());
        Set<CSV> csvs;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
        String dateString = dateFormat.format(Common.yesterday());
        String fileNameForGen;



        for(String fileName : fileNames){
            csvs = getRecentStatusUpload(fileName,Common.yesterday());
            fileNameForGen = fileName+dateString+".csv";

            logger.debug("fileName4Gen "+fileNameForGen);

            int result = exportData(fileNameForGen, csvs);
            logExport(fileName, result);
        }


    }


    private void logExport(String fileName, int status){

    }


    private int exportData(String fileName, Set<CSV> csvs){

        int result = 0;

        String dirFile = exportDirecotry + fileName;

        FileWriter fileWriter = null;

        try{
            fileWriter = new FileWriter(dirFile);
        }catch (Exception ex){
            logger.error("error export because of "+ex.toString());

            return result;
        }

        try{
            CSVUtils.writeLine(fileWriter, Arrays.asList("NCLI","ND_POTS","NAMA_PELANGGAN",
                    "NO_HP","EMAIL","NAMA_PEMILIK","ALAMAT",
                    "KOTA","REGIONAL","SMS_CONTENT","SENDER_ID",
                    "SENT_DATE","SENT_STATUS","REPLY_CONTENT",
                    "REPLY_STATUS","REPLY_DATE"));
        }catch (Exception ex){
            logger.error("error create header because of "+ex.toString());
            result = 2;
            return result;
        }

        try{
            for(CSV csv : csvs){
                List<String> list = new ArrayList<>();
                list.add(csv.getNcli());
                list.add(csv.getNdPots());
                list.add(csv.getNamaPelanggan());
                list.add(csv.getNoHP());
                list.add(csv.getEmail());
                list.add(csv.getNamaPemilik());
                list.add(csv.getAlamat());
                list.add(csv.getKota());
                list.add(csv.getRegional());
                list.add(csv.getContent());
                list.add(csv.getSenderID());
                list.add(csv.getSentDate()+"");
                list.add(csv.getStatusDesc());
                list.add(csv.getReplyContent());
                list.add(csv.getReplyStatusDesc());
                list.add(csv.getReplyDate()+"");

                CSVUtils.writeLine(fileWriter,list);


            }
        }catch (Exception ex){
            logger.error("error insert content because of "+ex.toString());

            result = 3;
            return result;
        }

        try{
            fileWriter.flush();
            fileWriter.close();

        }catch (Exception e){
            logger.error("error close file because of "+e.toString());
            result = 4;

            return result;
        }


        result = 1;

        return result;

    }

    private Set<CSV> getRecentStatusUpload(String fileName){
        Set<CSV> csvs = csvRepository.findByFileName(fileName);

        return csvs;
    }

    private Set<CSV> getRecentStatusUpload(String fileName,Date date){
        Set<CSV> csvs = csvRepository.findByFileNameAndSentDate(fileName,date);

        return csvs;
    }


    private Set<UploadHistory> getByUploadData(){
        Date yesterday = Common.yesterday();

        logger.debug("get upload data");
        Set<UploadHistory> uploadHistories = uploadHistoryRepository.findByUploadDate(yesterday);

        return uploadHistories;

    }

    private Set<String> getFileNames(Set<UploadHistory> uploadHistories){

        Set<String> files = new HashSet<>();

        for(UploadHistory uploadHistory : uploadHistories){
            logger.debug("file name "+uploadHistory.getFileName());
            files.add(uploadHistory.getFileName());
        }

        return files;
    }
}
