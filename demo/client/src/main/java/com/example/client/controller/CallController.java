package com.example.client.controller;

import com.example.client.service.call.CallCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author long
 * @date 2020/8/8
 */
@RestController
@RequestMapping("/")
@RefreshScope
@Slf4j
public class CallController {
    @Autowired
    private CallCenterService callCenterService;

    @GetMapping(path = "call")
    public String call(String channel) {
        return callCenterService.onCall(channel);
    }

}
