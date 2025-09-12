package com.ruoyi.common.utils;

import java.util.Random;

public class UserNicknameUtils {
    // 昵称用词，偏向网络风格
    private static final String[] NICKNAME_WORDS = {
            "小", "大", "萌", "呆", "酷", "喵", "汪", "星", "月", "云",
            "糖", "果", "豆", "猫", "狗", "熊", "兔", "风", "晴", "雪",
            "乐", "宝", "灵", "酱", "仔", "儿", "侠", "咕", "咪", "泡"
    };

    private String nickname;


    // 生成随机中文昵称
    public static String generateRandomChineseNickname() {
        Random random = new Random();
        // 昵称长度随机为2-4个字
        int length = random.nextInt(3) + 2; // 2, 3, or 4
        StringBuilder nickname = new StringBuilder();

        for (int i = 0; i < length; i++) {
            nickname.append(NICKNAME_WORDS[random.nextInt(NICKNAME_WORDS.length)]);
        }

        return nickname.toString();
    }
}
