package com.example.bamboo.javaBean;


public class WordMenuList {

    private String createdAt;
    private String newTablename;
    private String objectId;
    private String updatedAt;
    private String vocAge;
    private Integer vocID;
    private String vocLevel;
    private String vocTitle;
    private String vocWordnum;

    public WordMenuList(String vocAge, String vocLevel, String vocTitle, String vocWordnum) {
        this.vocAge = vocAge;
        this.vocLevel = vocLevel;
        this.vocTitle = vocTitle;
        this.vocWordnum = vocWordnum;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getNewTablename() {
        return newTablename;
    }

    public void setNewTablename(String newTablename) {
        this.newTablename = newTablename;
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

    public String getVocAge() {
        return vocAge;
    }

    public void setVocAge(String vocAge) {
        this.vocAge = vocAge;
    }

    public Integer getVocID() {
        return vocID;
    }

    public void setVocID(Integer vocID) {
        this.vocID = vocID;
    }

    public String getVocLevel() {
        return vocLevel;
    }

    public void setVocLevel(String vocLevel) {
        this.vocLevel = vocLevel;
    }

    public String getVocTitle() {
        return vocTitle;
    }

    public void setVocTitle(String vocTitle) {
        this.vocTitle = vocTitle;
    }

    public String getVocWordnum() {
        return vocWordnum;
    }

    public void setVocWordnum(String vocWordnum) {
        this.vocWordnum = vocWordnum;
    }
}
