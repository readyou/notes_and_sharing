package com.example.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @author long
 * @date 2020/8/8
 */
@RestController
@RequestMapping("/hello")
@RefreshScope
@Slf4j
public class HelloController {
    @Value("${hello.message}")
    private String message;

    @PostConstruct
    private void init() {
        log.info("init");
    }

    @EventListener(RefreshScopeRefreshedEvent.class)
    public void onRefresh(RefreshScopeRefreshedEvent event) {
        log.info("OnRefresh: {}", event);
    }

    @RequestMapping(path = "message")
    public String getMessage() {
        return message;
    }
}
