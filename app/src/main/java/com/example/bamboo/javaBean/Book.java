package com.example.bamboo.javaBean;

public class Book {



    private Integer bookId;
    private String bookTitle;
    private ColorcoverDTO colorcover;
    private String createdAt;
    private Integer goldCoin;
    private String level;
    private String objectId;
    private String updatedAt;

    public static class ColorcoverDTO {
        private String type;
        private String cdn;
        private String filename;
        private String url;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public ColorcoverDTO getColorcover() {
        return colorcover;
    }

    public void setColorcover(ColorcoverDTO colorcover) {
        this.colorcover = colorcover;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getGoldCoin() {
        return goldCoin;
    }

    public void setGoldCoin(Integer goldCoin) {
        this.goldCoin = goldCoin;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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
}
