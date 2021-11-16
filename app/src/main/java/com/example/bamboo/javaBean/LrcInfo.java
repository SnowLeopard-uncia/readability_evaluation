package com.example.bamboo.javaBean;

import java.util.Map;

//歌词实体类
public class LrcInfo {
    private String title; //music title
    private String artist; //artist name
    private String album; //album name
    private String bySomeBody; //the lrc maker
    private String offset; //the time delay or bring forward
    private Map<Long,String> info;//保存歌词信息和时间点一一对应的Map

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getBySomeBody() {
        return bySomeBody;
    }

    public void setBySomeBody(String bySomeBody) {
        this.bySomeBody = bySomeBody;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public Map<Long, String> getInfo() {
        return info;
    }

    public void setInfo(Map<Long, String> info) {
        this.info = info;
    }
}
