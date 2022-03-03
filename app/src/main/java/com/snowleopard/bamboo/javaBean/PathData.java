package com.snowleopard.bamboo.javaBean;

public class PathData {
    private float xPath;
    private float yPath;
    private String word;

    public PathData(float xPath, float yPath, String word) {
        this.xPath = xPath;
        this.yPath = yPath;
        this.word = word;
    }

    public float getxPath() {
        return xPath;
    }

    public void setxPath(float xPath) {
        this.xPath = xPath;
    }

    public float getyPath() {
        return yPath;
    }

    public void setyPath(float yPath) {
        this.yPath = yPath;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
