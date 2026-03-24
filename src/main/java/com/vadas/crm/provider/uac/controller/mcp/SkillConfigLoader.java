package com.vadas.crm.provider.uac.controller.mcp;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class SkillConfigLoader {

    public static SkillConfig load() throws Exception {
        String baseDir = System.getProperty("user.dir");
        File configFile = new File(baseDir, "config/skill-config.json");

        if (!configFile.exists()) {
            throw new RuntimeException("配置文件不存在: " + configFile.getAbsolutePath());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        SkillConfig config = objectMapper.readValue(configFile, SkillConfig.class);

        if (isBlank(config.getTokenUrl())) {
            throw new RuntimeException("tokenUrl 未配置");
        }

        if (isBlank(config.getBasicAuthUsername())) {
            throw new RuntimeException("basicAuthUsername 未配置");
        }

        if (isBlank(config.getBasicAuthPassword())) {
            throw new RuntimeException("basicAuthPassword 未配置");
        }

        if (isBlank(config.getScope())) {
            config.setScope("server");
        }

        if (isBlank(config.getGrantType())) {
            config.setGrantType("password");
        }

        if (isBlank(config.getCrmUsername())) {
            throw new RuntimeException("crmUsername 未配置");
        }

        if (isBlank(config.getCrmPassword())) {
            throw new RuntimeException("crmPassword 未配置");
        }

        if (isBlank(config.getMcpApplicationYmlPath())) {
            throw new RuntimeException("mcpApplicationYmlPath 未配置");
        }

        return config;
    }

    private static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
}
