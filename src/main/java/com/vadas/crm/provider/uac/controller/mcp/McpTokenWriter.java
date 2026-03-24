package com.vadas.crm.provider.uac.controller.mcp;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class McpTokenWriter {

    public void writeTokenToApplicationYml(String applicationYmlPath, String token) throws Exception {
        File file = new File(applicationYmlPath);
        if (!file.exists()) {
            throw new RuntimeException("application.yml 不存在: " + file.getAbsolutePath());
        }

        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        StringBuilder result = new StringBuilder();

        boolean inCrmBlock = false;
        boolean inApiBlock = false;
        boolean tokenUpdated = false;

        for (String line : lines) {
            String trimmed = line.trim();
            int indent = countLeadingSpaces(line);

            if (indent == 0 && trimmed.startsWith("crm:")) {
                inCrmBlock = true;
                inApiBlock = false;
                result.append(line).append(System.lineSeparator());
                continue;
            }

            if (inCrmBlock && indent <= 0 && !trimmed.isEmpty() && !trimmed.startsWith("crm:")) {
                inCrmBlock = false;
                inApiBlock = false;
            }

            if (inCrmBlock && indent == 2 && trimmed.startsWith("api:")) {
                inApiBlock = true;
                result.append(line).append(System.lineSeparator());
                continue;
            }

            if (inApiBlock && indent <= 2 && !trimmed.isEmpty() && !trimmed.startsWith("api:")) {
                inApiBlock = false;
            }

            if (inCrmBlock && inApiBlock && indent == 4 && trimmed.startsWith("token:")) {
                result.append("    token: \"").append(escapeYaml(token)).append("\"").append(System.lineSeparator());
                tokenUpdated = true;
                continue;
            }

            result.append(line).append(System.lineSeparator());
        }

        if (!tokenUpdated) {
            throw new RuntimeException("未在 application.yml 中找到 crm.api.token 配置项，请先确认文件结构正确");
        }

        Files.write(file.toPath(), result.toString().getBytes(StandardCharsets.UTF_8));
    }

    private int countLeadingSpaces(String line) {
        int count = 0;
        while (count < line.length() && line.charAt(count) == ' ') {
            count++;
        }
        return count;
    }

    private String escapeYaml(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
