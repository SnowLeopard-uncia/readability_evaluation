package com.snowleopard.bamboo.javaBean;

public class BookHome {

    private Integer book_id;
    private String book_title;
    private ColorcoverDTO colorcover;
    private String cover_url;
    private String createdAt;
    private Integer gold_coin;
    private String level;
    private String objectId;
    private String updatedAt;

    public BookHome(Integer goldCoin, String level) {
        this.gold_coin = goldCoin;
        this.level = level;
    }


    public static class ColorcoverDTO {
        private String __type;
        private String cdn;
        private String filename;
        private String url;

        public String getType() {
            return __type;
        }

        public void setType(String type) {
            this.__type = type;
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

    public Integer getBookId() {
        return book_id;
    }

    public void setBookId(Integer bookId) {
        this.book_id = bookId;
    }

    public String getBookTitle() {
        return book_title;
    }

    public void setBookTitle(String bookTitle) {
        this.book_title = bookTitle;
    }

    public ColorcoverDTO getColorcover() {
        return colorcover;
    }

    public void setColorcover(ColorcoverDTO colorcover) {
        this.colorcover = colorcover;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getGoldCoin() {
        return gold_coin;
    }

    public void setGoldCoin(Integer goldCoin) {
        this.gold_coin = goldCoin;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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
}
