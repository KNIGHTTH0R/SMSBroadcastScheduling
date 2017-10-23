package com.tlkm.broadcast5g.repository.database;

import com.tlkm.broadcast5g.model.CSV;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CSVRepository extends CrudRepository<CSV,Long> {

    Set<CSV> findByFileName(String fileName);


    Set<CSV> findByPin(String pin);

    Set<CSV> findByStatusId(int id);

}
