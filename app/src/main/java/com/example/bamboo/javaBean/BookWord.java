package com.example.bamboo.javaBean;

import java.util.List;

public class BookWord {
    private Integer book_id;
    private String createdAt;
    private String objectId;
    private String updatedAt;
    private List<String> word_list;
    private List<String> word;

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getWord_list() {
        return word_list;
    }

    public void setWord_list(List<String> word_list) {
        this.word_list = word_list;
    }

    public List<String> getWord() {
        return word;
    }

    public void setWord(List<String> word) {
        this.word = word;
    }
}
