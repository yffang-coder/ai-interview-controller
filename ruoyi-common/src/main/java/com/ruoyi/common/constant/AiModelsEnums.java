package com.ruoyi.common.constant;

public enum AiModelsEnums {
    BAIDU_ERNIE_3_5_8K("ernie-3.5-8k","https://qianfan.baidubce.com/v2/chat/completions"),
    BAIDU_ERNIE_3_5_128K("ernie-3.5-128k","https://qianfan.baidubce.com/v2/chat/completions"),
    DEEP_SEEK_CHAT("deepseek-chat","https://api.deepseek.com/chat/completions");
    private String modelName;
    private String url;

    AiModelsEnums(String modelName, String url) {
        this.modelName = modelName;
        this.url = url;
    }

    public String getModelName() {
        return modelName;
    }

    public String getUrl() {
        return url;
    }
}
