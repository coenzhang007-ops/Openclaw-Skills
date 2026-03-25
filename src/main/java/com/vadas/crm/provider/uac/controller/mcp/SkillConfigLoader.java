package com.vadas.crm.provider.uac.controller.mcp;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class SkillConfigLoader {

    public static SkillConfig load() throws Exception {
        File configFile = resolveConfigFile();

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

    private static File resolveConfigFile() {
        File workingDirConfig = new File(System.getProperty("user.dir"), "config/skill-config.json");
        if (workingDirConfig.exists()) {
            return workingDirConfig;
        }

        File jarDirConfig = new File(resolveAppBaseDir(), "config/skill-config.json");
        return jarDirConfig;
    }

    private static File resolveAppBaseDir() {
        try {
            URI location = CrmSkillMain.class.getProtectionDomain().getCodeSource().getLocation().toURI();
            File codeSource = new File(location);

            if (codeSource.isFile()) {
                File parent = codeSource.getParentFile();
                if (parent != null) {
                    File appRoot = parent.getParentFile();
                    if (appRoot != null) {
                        return appRoot;
                    }
                    return parent;
                }
            }

            if (codeSource.isDirectory()) {
                return codeSource;
            }
        } catch (Exception ignored) {
        }

        try {
            String rawPath = CrmSkillMain.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String decodedPath = URLDecoder.decode(rawPath, StandardCharsets.UTF_8.name());
            File codeSource = new File(decodedPath);
            if (codeSource.isFile()) {
                File parent = codeSource.getParentFile();
                if (parent != null && parent.getParentFile() != null) {
                    return parent.getParentFile();
                }
                return parent != null ? parent : new File(System.getProperty("user.dir"));
            }
            if (codeSource.isDirectory()) {
                return codeSource;
            }
        } catch (Exception ignored) {
        }

        return new File(System.getProperty("user.dir"));
    }

    private static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
}
