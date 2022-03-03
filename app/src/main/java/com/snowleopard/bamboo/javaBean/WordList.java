package com.snowleopard.bamboo.javaBean;

public class WordList {

    private String word;
    private String soundmard;
    private String zh;
    private int isClick=0;

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
