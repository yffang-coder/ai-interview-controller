package com.ruoyi.ai.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AiResponseBody {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;

    // Getter and Setter for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter for object
    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    // Getter and Setter for created
    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    // Getter and Setter for model
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    // Getter and Setter for choices
    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    // Getter and Setter for usage
    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    // Nested Choice class
    public static class Choice {
        private int index;
        private Message message;
        private String finishReason;
        private int flag;

        // Getter and Setter for index
        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        // Getter and Setter for message
        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }

        // Getter and Setter for finishReason
        public String getFinishReason() {
            return finishReason;
        }

        public void setFinishReason(String finishReason) {
            this.finishReason = finishReason;
        }

        // Getter and Setter for flag
        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }
    }

    // Nested Message class
    public static class Message {
        private String role;
        private String content;

        // Getter and Setter for role
        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        // Getter and Setter for content
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    // Nested Usage class
    public static class Usage {
        private int promptTokens;
        private int completionTokens;
        private int totalTokens;

        // Getter and Setter for promptTokens
        public int getPromptTokens() {
            return promptTokens;
        }

        public void setPromptTokens(int promptTokens) {
            this.promptTokens = promptTokens;
        }

        // Getter and Setter for completionTokens
        public int getCompletionTokens() {
            return completionTokens;
        }

        public void setCompletionTokens(int completionTokens) {
            this.completionTokens = completionTokens;
        }

        // Getter and Setter for totalTokens
        public int getTotalTokens() {
            return totalTokens;
        }

        public void setTotalTokens(int totalTokens) {
            this.totalTokens = totalTokens;
        }
    }
}