package com.example.client.controller;

import com.example.spidemo.SpiBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @author long
 * @date 2020/8/8
 */
@RestController
@RequestMapping("/")
@RefreshScope
@Slf4j
public class SpiController {
    private SpiBase spiBase;

    @PostConstruct
    private void init() {
        spiBase = SpiBase.getSpiDemoFactory();
    }

    @GetMapping(path = "spi")
    public String spiHello() {
        return spiBase.hello();
    }

}
