package com.ruoyi.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ai")
public class AiProperties {
    private String apiKey;
    private String token;
    private String token_url;
    private String deepseek_token;
    private String doubao_token;

    private String qianwen_token;

    private String tengxun_hunyuan_token;

    public String getTengxun_hunyuan_token() {
        return tengxun_hunyuan_token;
    }

    public void setTengxun_hunyuan_token(String tengxun_hunyuan_token) {
        this.tengxun_hunyuan_token = tengxun_hunyuan_token;
    }

    public  String getQianwen_token() {
        return qianwen_token;
    }

    public void setQianwen_token(String qianwen_token) {
        this.qianwen_token = qianwen_token;
    }
    public String getDoubao_token() {
        return doubao_token;
    }

    public void setDoubao_token(String doubao_token) {
        this.doubao_token = doubao_token;
    }



    public String getDeepseek_token() {
        return deepseek_token;
    }

    public void setDeepseek_token(String deepseek_token) {
        this.deepseek_token = deepseek_token;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken_url() {
        return token_url;
    }

    public void setToken_url(String token_url) {
        this.token_url = token_url;
    }
}
