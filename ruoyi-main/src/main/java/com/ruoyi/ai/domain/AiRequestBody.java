package com.ruoyi.ai.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AiRequestBody {

    private List<AiChatMessage> messages;
    private Boolean stream;
    private Float temperature;

    private Integer max_tokens;
    private Float top_p;

    private Float penalty_score;
    private List<AiChatMessage> stop;
    private Float frequency_penalty;

    private Float presence_penalty;
    private Float repetition_penalty;

    private String user;
    private String model;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<AiChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<AiChatMessage> messages) {
        this.messages = messages;
    }

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Integer getMax_tokens() {
        return max_tokens;
    }

    public void setMax_tokens(Integer max_tokens) {
        this.max_tokens = max_tokens;
    }

    public Float getTop_p() {
        return top_p;
    }

    public void setTop_p(Float top_p) {
        this.top_p = top_p;
    }

    public Float getPenalty_score() {
        return penalty_score;
    }

    public void setPenalty_score(Float penalty_score) {
        this.penalty_score = penalty_score;
    }

    public List<AiChatMessage> getStop() {
        return stop;
    }

    public void setStop(List<AiChatMessage> stop) {
        this.stop = stop;
    }

    public Float getFrequency_penalty() {
        return frequency_penalty;
    }

    public void setFrequency_penalty(Float frequency_penalty) {
        this.frequency_penalty = frequency_penalty;
    }

    public Float getPresence_penalty() {
        return presence_penalty;
    }

    public void setPresence_penalty(Float presence_penalty) {
        this.presence_penalty = presence_penalty;
    }

    public Float getRepetition_penalty() {
        return repetition_penalty;
    }

    public void setRepetition_penalty(Float repetition_penalty) {
        this.repetition_penalty = repetition_penalty;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
