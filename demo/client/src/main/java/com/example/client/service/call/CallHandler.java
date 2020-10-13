package com.example.client.service.call;

import java.util.Set;

/**
 * 模板，可以是接口，也可以是抽象类
 */
public interface CallHandler {
    /**
     * 回复呼叫。
     * <p>
     * 覆盖此方法实现业务逻辑。
     *
     * @param channel 呼入channel
     * @return
     */
    String reply(String channel);

    /**
     * 获取需要处理的呼叫渠道。
     * <p>
     * 覆盖此方法，提供需要处理的呼叫渠道。
     *
     * @return
     */
    Set<String> getChannelsToHandle();
}
