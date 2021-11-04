package com.example.bamboo.javaBean;

import java.io.File;
import java.util.List;

public class Book {
    private int book_id;
    private char grade;
    private int gold_coin;
    private int word_count;
    private String content;
    private String suit_age;
    private String level;
    private String author;
    private String book_title;
    private File colorcover;
    private List<String[]> word_list;

    public Book(int book_id, char grade, int gold_coin) {
        this.book_id = book_id;
        this.grade = grade;
        this.gold_coin = gold_coin;
    }

    public Book(int book_id, char grade, int gold_coin, File colorcover) {
        this.book_id = book_id;
        this.grade = grade;
        this.gold_coin = gold_coin;
        this.colorcover = colorcover;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public char getGrade() {
        return grade;
    }

    public void setGrade(char grade) {
        this.grade = grade;
    }

    public int getGold_coin() {
        return gold_coin;
    }

    public void setGold_coin(int gold_coin) {
        this.gold_coin = gold_coin;
    }

    public int getWord_count() {
        return word_count;
    }

    public void setWord_count(int word_count) {
        this.word_count = word_count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSuit_age() {
        return suit_age;
    }

    public void setSuit_age(String suit_age) {
        this.suit_age = suit_age;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public File getColorcover() {
        return colorcover;
    }

    public void setColorcover(File colorcover) {
        this.colorcover = colorcover;
    }



}
