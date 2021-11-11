package com.example.bamboo.javaBean;


public class Audio {

    private String duration;
    private String level;
    private LrcFileDTO lrcFile;
    private MusicFileDTO musicFile;
    private String name;
    private String singer;


    public static class LrcFileDTO {
        private String cdn;
        private String filename;
        private String url;
    }


    public static class MusicFileDTO {
        private String cdn;
        private String filename;
        private String url;
    }
}
