package com.tlkm.broadcast5g.repository.database;

import com.tlkm.broadcast5g.model.SMSInformation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SMSInformationRepository extends CrudRepository<SMSInformation,Long> {
}
