package com.example.bamboo.javaBean;

public class BookItem {
    private int id;
    private char grade;
    private int imageId;
    private int coin;

    public BookItem(int id, char grade, int imageId, int coin) {
        this.id = id;
        this.grade = grade;
        this.imageId = imageId;
        this.coin = coin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getGrade() {
        return grade;
    }

    public void setGrade(char grade) {
        this.grade = grade;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
}
