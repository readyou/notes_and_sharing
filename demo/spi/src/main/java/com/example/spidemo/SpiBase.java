package com.example.spidemo;

import java.util.ServiceLoader;

public interface SpiBase {

    String hello();

    static SpiBase getSpiDemoFactory() {
        ServiceLoader<SpiBase> cicadaBeanFactories = ServiceLoader.load(SpiBase.class);
        if (cicadaBeanFactories.iterator().hasNext()) {
            SpiBase next = cicadaBeanFactories.iterator().next();
            System.out.println("Found spi: " + next.getClass().getName());
            return next;
        }
        return new DefaultSpiBean();
    }

    public static class DefaultSpiBean implements SpiBase {
        @Override
        public String hello() {
            return "Default hello";
        }
    }
}
