package com.example.spidemo2;

import com.example.spidemo.SpiBase;

/**
 * @author long
 * @version 1.0
 * @date 2020/8/12
 */
public class SpiDemo2Bean implements SpiBase {
    @Override
    public String hello() {
        return "Hi, this is SpiDemo2Bean.";
    }
}
