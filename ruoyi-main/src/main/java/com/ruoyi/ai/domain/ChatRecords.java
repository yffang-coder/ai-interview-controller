package com.ruoyi.ai.domain;

public class ChatRecords {
    private InterviewRecords question;
    private InterviewRecords answer;

    public InterviewRecords getQuestion() {
        return question;
    }

    public void setQuestion(InterviewRecords question) {
        this.question = question;
    }

    public InterviewRecords getAnswer() {
        return answer;
    }

    public void setAnswer(InterviewRecords answer) {
        this.answer = answer;
    }
}
