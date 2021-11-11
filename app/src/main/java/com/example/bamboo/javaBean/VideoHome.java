package com.example.bamboo.javaBean;

public class VideoHome {

    private String createdAt;
    private String objectId;
    private String updatedAt;
    private Integer videoID;
    private String videoLevel;
    private String videoName;
    private VideoPicDTO videoPic;
    private Integer videoPrice;

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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

    public Integer getVideoID() {
        return videoID;
    }

    public void setVideoID(Integer videoID) {
        this.videoID = videoID;
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

    public Integer getVideoPrice() {
        return videoPrice;
    }

    public void setVideoPrice(Integer videoPrice) {
        this.videoPrice = videoPrice;
    }
}
