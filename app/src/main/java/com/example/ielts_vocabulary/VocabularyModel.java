package com.example.ielts_vocabulary;

import java.util.ArrayList;

public class VocabularyModel
{
    int id;
    String sentence;
    String answer;

    public VocabularyModel(int id, String sentence, String answer) {
        this.id = id;
        this.sentence = sentence;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
