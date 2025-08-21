package com.ruoyi.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mp")
public class MpProperties {
    private String appid;
    private String secret;
    private String grantType;
    private String tokenUrl;

    public String getAppid() {
        return appid;
    }

    public String getSecret() {
        return secret;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }
}
