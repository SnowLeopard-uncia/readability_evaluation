package com.snowleopard.bamboo.javaBean;

public class WordList {

    private String word;
    private String soundmard;
    private String phonetic;
    private String zh;
    private int isClick=0;

    public WordList(String word, String soundmard, String phonetic, String zh) {
        this.word = word;
        this.soundmard = soundmard;
        this.phonetic = phonetic;
        this.zh = zh;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public int getIsClick() {
        return isClick;
    }

    public void setIsClick(int isClick) {
        this.isClick = isClick;
    }

    public WordList(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getSoundmard() {
        return soundmard;
    }

    public void setSoundmard(String soundmard) {
        this.soundmard = soundmard;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }
}
