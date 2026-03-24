package com.vadas.crm.provider.uac.controller.mcp;

public class CrmSkillMain {

    public static void main(String[] args) {
        String action = (args != null && args.length > 0) ? args[0] : "login";

        try {
            SkillConfig config = SkillConfigLoader.load();
            CrmTokenClient client = new CrmTokenClient();

            if ("login".equalsIgnoreCase(action)) {
                String token = client.loginAndGetToken(config);
                System.out.println("{\"success\":true,\"action\":\"login\",\"access_token\":\"" + escapeJson(token) + "\"}");
                return;
            }

            if ("raw".equalsIgnoreCase(action)) {
                String raw = client.loginAndGetRawResponse(config);
                System.out.println("{\"success\":true,\"action\":\"raw\",\"response\":\"" + escapeJson(raw) + "\"}");
                return;
            }

            System.out.println("{\"success\":false,\"message\":\"不支持的action: " + escapeJson(action) + "\"}");
            System.exit(1);

        } catch (Exception e) {
            System.out.println("{\"success\":false,\"action\":\"" + escapeJson(action) + "\",\"message\":\"" + escapeJson(e.getMessage()) + "\"}");
            System.exit(1);
        }
    }

    private static String escapeJson(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
    }
}
