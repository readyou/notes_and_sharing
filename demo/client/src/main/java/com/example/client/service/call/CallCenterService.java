package com.example.client.service.call;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 呼叫中心
 *
 * @author long
 * @version 1.0
 * @date 2020/8/13
 */
@Service
@Slf4j
public class CallCenterService {
    @Autowired
    private Map<String, CallHandler> callHandlerMap;

    private final Map<String, CallHandler> channelHandlerMap = new HashMap<>();

    /**
     * 初始化函数，收集CallHandler对象，并将呼叫渠道与之建立关联。
     */
    @PostConstruct
    private void init() {
        callHandlerMap.forEach((key, handler) -> {
            Set<String> channels = handler.getChannelsToHandle();
            channels.forEach(channel -> {
                if (channelHandlerMap.containsKey(channel)) {
                    log.error("channel duplicated: {}", channel);
                    return;
                }
                channelHandlerMap.put(channel, handler);
            });
        });
    }

    /**
     *
     * @param callChannel
     * @return
     */
    public String onCall(String callChannel) {
        CallHandler handler = channelHandlerMap.get(callChannel);
        if (handler == null) {
            return "Reply from team default";
        }
        return handler.reply(callChannel);
    }
}
