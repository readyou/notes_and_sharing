package com.example.client.service.call.impl;

import com.example.client.service.call.CallHandler;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author long
 * @version 1.0
 * @date 2020/8/13
 */
@Service
public class CallHandler2Impl implements CallHandler {
    @Override
    public String reply(String channel) {
        return "Reply to " + channel + " from team 2";
    }

    @Override
    public Set<String> getChannelsToHandle() {
        return Set.of("channel2", "channel22");
    }
}
