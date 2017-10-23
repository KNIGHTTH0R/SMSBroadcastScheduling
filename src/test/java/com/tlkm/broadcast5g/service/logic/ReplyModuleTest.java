package com.tlkm.broadcast5g.service.logic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ReplyModuleTest {

    @Autowired
    ReplyModule replyModule;

    Logger logger = LoggerFactory.getLogger(ReplyModuleTest.class);

    @Test
    public void replyProcess() throws Exception {

        //content : "Y
        String content = "Y Pt432";

        String reply = replyModule.replyProcess("test",content,"encrypt","opt");


        logger.debug(reply);


    }

}