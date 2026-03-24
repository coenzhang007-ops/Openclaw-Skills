package com.vadas.crm.provider.uac.controller.mcp;

public class SkillConfig {

    private String tokenUrl;
    private String basicAuthUsername;
    private String basicAuthPassword;
    private String scope;
    private String grantType;
    private String crmUsername;
    private String crmPassword;

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getBasicAuthUsername() {
        return basicAuthUsername;
    }

    public void setBasicAuthUsername(String basicAuthUsername) {
        this.basicAuthUsername = basicAuthUsername;
    }

    public String getBasicAuthPassword() {
        return basicAuthPassword;
    }

    public void setBasicAuthPassword(String basicAuthPassword) {
        this.basicAuthPassword = basicAuthPassword;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getCrmUsername() {
        return crmUsername;
    }

    public void setCrmUsername(String crmUsername) {
        this.crmUsername = crmUsername;
    }

    public String getCrmPassword() {
        return crmPassword;
    }

    public void setCrmPassword(String crmPassword) {
        this.crmPassword = crmPassword;
    }
}
