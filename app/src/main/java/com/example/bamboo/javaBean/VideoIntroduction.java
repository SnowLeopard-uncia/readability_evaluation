package com.example.bamboo.javaBean;

public class VideoIntroduction {

    private String author;
    private String createdAt;
    private String introduce;
    private Mp4fileDTO mp4file;
    private String objectId;
    private String src;
    private String topic;
    private String updatedAt;
    private String videoAge;
    private String videoLevel;
    private String videoName;
    private VideoPicDTO videoPic;
    private VideoSrtDTO videoSrt;
    private String videoWordnum;

    public static class Mp4fileDTO {
        private String __type;
        private String cdn;
        private String filename;
        private String url;

        public String get__type() {
            return __type;
        }

        public void set__type(String __type) {
            this.__type = __type;
        }

        public String getCdn() {
            return cdn;
        }

        public void setCdn(String cdn) {
            this.cdn = cdn;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class VideoPicDTO {
        private String __type;
        private String cdn;
        private String filename;
        private String url;

        public String get__type() {
            return __type;
        }

        public void set__type(String __type) {
            this.__type = __type;
        }

        public String getCdn() {
            return cdn;
        }

        public void setCdn(String cdn) {
            this.cdn = cdn;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class VideoSrtDTO {
        private String __type;
        private String cdn;
        private String filename;
        private String url;

        public String get__type() {
            return __type;
        }

        public void set__type(String __type) {
            this.__type = __type;
        }

        public String getCdn() {
            return cdn;
        }

        public void setCdn(String cdn) {
            this.cdn = cdn;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Mp4fileDTO getMp4file() {
        return mp4file;
    }

    public void setMp4file(Mp4fileDTO mp4file) {
        this.mp4file = mp4file;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getVideoAge() {
        return videoAge;
    }

    public void setVideoAge(String videoAge) {
        this.videoAge = videoAge;
    }

    public String getVideoLevel() {
        return videoLevel;
    }

    public void setVideoLevel(String videoLevel) {
        this.videoLevel = videoLevel;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public VideoPicDTO getVideoPic() {
        return videoPic;
    }

    public void setVideoPic(VideoPicDTO videoPic) {
        this.videoPic = videoPic;
    }

    public VideoSrtDTO getVideoSrt() {
        return videoSrt;
    }

    public void setVideoSrt(VideoSrtDTO videoSrt) {
        this.videoSrt = videoSrt;
    }

    public String getVideoWordnum() {
        return videoWordnum;
    }

    public void setVideoWordnum(String videoWordnum) {
        this.videoWordnum = videoWordnum;
    }
}
