package com.snowleopard.bamboo.javaBean;

public class LevelText {

    private String grade;

    public String getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "\n" +
                "grade= " + grade;
    }
}
