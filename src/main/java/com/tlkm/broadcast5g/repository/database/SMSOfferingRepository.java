package com.tlkm.broadcast5g.repository.database;

import com.tlkm.broadcast5g.model.SMSOffering;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SMSOfferingRepository extends CrudRepository<SMSOffering,Long> {

    Set<SMSOffering> findByPin(String pin);

}
