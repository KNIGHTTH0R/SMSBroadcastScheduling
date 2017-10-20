package com.tlkm.broadcast5g.service.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SyncSample {


    Logger logger = LoggerFactory.getLogger(SyncSample.class);

    @Async
    public void testSample(String data){

        logger.debug("data : "+data+" with threads : "+Thread.currentThread().getName());
    }

}

