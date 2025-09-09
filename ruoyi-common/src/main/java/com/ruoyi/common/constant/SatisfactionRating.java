package com.ruoyi.common.constant;

public enum SatisfactionRating {
    VERY_DISSATISFIED(1, "非常不满意"),
    DISSATISFIED(2, "不满意"),
    NEUTRAL(3, "一般"),
    SATISFIED(4, "满意"),
    VERY_SATISFIED(5, "非常满意");

    private final int value;
    private final String description;

    SatisfactionRating(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static SatisfactionRating fromValue(int value) {
        for (SatisfactionRating rating : SatisfactionRating.values()) {
            if (rating.value == value) {
                return rating;
            }
        }
        throw new IllegalArgumentException("Invalid rating value: " + value);
    }
}
