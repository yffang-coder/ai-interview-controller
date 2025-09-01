package com.ruoyi.common.constant;

public enum AiModelsEnums {
    BAIDU_ERNIE_3_5_8K("ernie-lite","ernie-3.5-8k","https://qianfan.baidubce.com/v2/chat/completions"),
    BAIDU_ERNIE_3_5_128K("ernie-pro","ernie-3.5-128k","https://qianfan.baidubce.com/v2/chat/completions"),
    DEEP_SEEK_CHAT("deepSeek-chat","deepseek-chat","https://api.deepseek.com/chat/completions"),
    DOUBAO_DOUBAO_SEED_1_6("doubao-lite","doubao-lite-32k-240828","https://ark.cn-beijing.volces.com/api/v3/chat/completions"),
    ALIBABA_QWEN_PLUS("qwen-plus","qwen-plus","https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions"),
    TENGXUN_HUNYUAN_TURBOS_LATEST("hunyuan-turbos","hunyuan-turbos-latest","https://api.hunyuan.cloud/v1/chat/completions");
    private String modelName;

    private String ueName;
    private String url;

    AiModelsEnums(String ueName,String modelName, String url) {
        this.modelName = modelName;
        this.ueName = ueName;
        this.url = url;
    }

    public String getUeName() {
        return ueName;
    }

    public String getModelName() {
        return modelName;
    }

    public String getUrl() {
        return url;
    }

    public static AiModelsEnums getByModelName(String modelName) {
        for (AiModelsEnums value : values()) {
            if (value.modelName.equals(modelName)) {
                return value;
            }
        }
        return null;
    }

    public static AiModelsEnums getByUeName(String ueName) {
        for (AiModelsEnums value : values()) {
            if (value.ueName.equals(ueName)) {
                return value;
            }
        }
        return null;
    }
}
