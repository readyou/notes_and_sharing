package com.example.spidemo1;

import com.example.spidemo.SpiBase;

/**
 * @author long
 * @version 1.0
 * @date 2020/8/12
 */
public class SpiDemo1Bean implements SpiBase {
    @Override
    public String hello() {
        return "Hello, this is SpiDemo1Bean.";
    }
}
