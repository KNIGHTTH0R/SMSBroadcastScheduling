package com.tlkm.broadcast5g.service.dao;

import com.tlkm.broadcast5g.model.CSV;
import com.tlkm.broadcast5g.repository.database.CSVRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CSVDao {

    @Autowired
    CSVRepository csvRepository;

    public  void bulkSaveCSVs(Set<CSV> csvs){

        for(CSV csv : csvs){
            csvRepository.save(csv);
        }
    }

    public Set<CSV> getSMSData(){
        Set<CSV> csvs = csvRepository.findByStatusId(0);

        return csvs;
    }
}
