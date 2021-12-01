package com.example.bamboo.javaBean;

public class ResultData {
    private Double min;
    private Double max;
    private Double data;
    private String name;
    private String grade;

    public ResultData(Double min, Double max, Double data, String name, String grade) {
        this.min = min;
        this.max = max;
        this.data = data;
        this.name = name;
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getData() {
        return data;
    }

    public void setData(Double data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
