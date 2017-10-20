package com.tlkm.broadcast5g.repository.database;

import com.tlkm.broadcast5g.model.SMSCustomer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SMSCustomerRepository extends CrudRepository<SMSCustomer,Long> {
}
