package com.tlkm.broadcast5g.service.dao;

import com.tlkm.broadcast5g.model.LogReply;
import com.tlkm.broadcast5g.repository.database.LogReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogReplyDao {

    @Autowired
    LogReplyRepository logReplyRepository;


    public void saveLog(String encryptNo,String content,String optId,String shortCode){

        LogReply logReply = new LogReply();
        logReply.setEncryptNo(encryptNo);
        logReply.setContent(content);
        logReply.setOptId(optId);
        logReply.setShortCode(shortCode);

        logReplyRepository.save(logReply);
    }
}
