package com.tlkm.broadcast5g.repository.database;

import com.tlkm.broadcast5g.model.SMSReply;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SMSReplyRepository extends CrudRepository<SMSReply,Long> {
}
