package com.amazon.config;

import org.aeonbits.owner.ConfigCache;

public class ConfigManager {

    private ConfigManager() {
    }

    public static IConfiguration configuration() {
        return ConfigCache.getOrCreate(IConfiguration.class);
    }
}

