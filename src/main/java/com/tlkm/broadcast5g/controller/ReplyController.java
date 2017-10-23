package com.tlkm.broadcast5g.controller;

import com.tlkm.broadcast5g.service.logic.ReplyModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/t")
public class ReplyController {

    @Autowired
    private ReplyModule replyModule;

    @GetMapping("/reply")
    public String replySMS(
            @RequestParam(value="api-key")String apiKey,
            @RequestParam(value="reply-content") String replySMS){

        return replyModule.replyProcess(apiKey,replySMS);
    }
}
