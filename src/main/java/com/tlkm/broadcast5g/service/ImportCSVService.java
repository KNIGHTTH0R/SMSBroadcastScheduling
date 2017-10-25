package com.tlkm.broadcast5g.service;

import com.tlkm.broadcast5g.model.CSV;
import com.tlkm.broadcast5g.utils.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ImportCSVService  {

    Logger logger = LoggerFactory.getLogger(ImportCSVService.class);

    public Set<CSV> mapToCSVs(BufferedReader bufferedReader,String fileName){

       String line = "";
       int idx = 0;

       Set<CSV> csvs = new HashSet<>();
       CSV csv;

       try{

           while((line = bufferedReader.readLine())!=null){

               if(idx==0){
                   logger.debug("line idx 0 : "+line);
                   idx++;
                   continue;
               }

               logger.debug("line idx : "+idx+" "+line);
               csv = translateToCSV(line,fileName);

               csvs.add(csv);
              idx++;
           }

       }catch (Exception ex){
           logger.error("error because of "+ex.toString());
       }

       return csvs;
    }

    private CSV translateToCSV(String line,String fileName){
        CSV csv = new CSV();

        String data[] = line.split(",");


        try{
            csv.setNcli(data[0]!=null?data[0]:"");
        }catch (IndexOutOfBoundsException ex){

        }

        try{
            csv.setNdPots(data[1]!=null?data[1]:"");
        }catch (IndexOutOfBoundsException ex){
            csv.setNdPots("");

        }
        try{
            csv.setNamaPelanggan(data[2]!=null?data[2]:"");
        }catch (IndexOutOfBoundsException ex){
            csv.setNamaPelanggan("");

        }

        try{
            String noHp = data[3]!=null?data[3]:"";
            noHp = Common.trimMsisdn(noHp);
            csv.setNoHP(noHp);
        }catch (IndexOutOfBoundsException ex){
            csv.setNoHP("");
        }


        try{
            csv.setEmail(data[4]!=null?data[4]:"");
        }catch (IndexOutOfBoundsException ex){
            csv.setEmail("");
        }



        try{
            csv.setNamaPemilik(data[5]!=null?data[5]:"");
        }catch (IndexOutOfBoundsException ex){
            csv.setNamaPemilik("");
        }


        try{
            csv.setAlamat(data[6]!=null?data[6]:"");
        }catch (IndexOutOfBoundsException ex){
            csv.setAlamat("");
        }


        try{
            csv.setKota(data[7]!=null?data[7]:"");
        }catch (IndexOutOfBoundsException ex){
            csv.setKota("");

        }


        try{
            csv.setRegional(data[8]!=null?data[8]:"");
        }catch (IndexOutOfBoundsException ex){
            csv.setRegional("");

        }

        csv.setFileName(fileName);

        return csv;


    }
}
