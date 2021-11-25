package com.example.bamboo.javaBean;

import java.util.List;

public class BookIntroduction {
    private Double SYN_ParseTreeHeight;
    private Integer SYN_numSentences;
    private Integer TRAD_Wordnum;
    private Double Word_CTTR;
    private String author;
    private Integer book_id;
    private String book_title;
    private ColorcoverDTO colorcover;
    private String content;
    private String cover_url;
    private String createdAt;
    private Integer gold_coin;
    private String level;
    private String objectId;
    private String suit_age;
    private String suitage;
    private String theme;
    private String title;
    private String writer;
    private String updatedAt;
    private Integer word_count;
    private List<String> word_list;

    public static class ColorcoverDTO {
        private String __type;

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

        private String cdn;
        private String filename;
        private String url;
    }

    public Double getSYN_ParseTreeHeight() {
        return SYN_ParseTreeHeight;
    }

    public void setSYN_ParseTreeHeight(Double SYN_ParseTreeHeight) {
        this.SYN_ParseTreeHeight = SYN_ParseTreeHeight;
    }

    public Integer getSYN_numSentences() {
        return SYN_numSentences;
    }

    public void setSYN_numSentences(Integer SYN_numSentences) {
        this.SYN_numSentences = SYN_numSentences;
    }

    public Integer getTRAD_Wordnum() {
        return TRAD_Wordnum;
    }

    public void setTRAD_Wordnum(Integer TRAD_Wordnum) {
        this.TRAD_Wordnum = TRAD_Wordnum;
    }

    public Double getWord_CTTR() {
        return Word_CTTR;
    }

    public void setWord_CTTR(Double word_CTTR) {
        Word_CTTR = word_CTTR;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public ColorcoverDTO getColorcover() {
        return colorcover;
    }

    public void setColorcover(ColorcoverDTO colorcover) {
        this.colorcover = colorcover;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Integer getGold_coin() {
        return gold_coin;
    }

    public void setGold_coin(Integer gold_coin) {
        this.gold_coin = gold_coin;
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

    public String getSuit_age() {
        return suit_age;
    }

    public void setSuit_age(String suit_age) {
        this.suit_age = suit_age;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getWord_count() {
        return word_count;
    }

    public void setWord_count(Integer word_count) {
        this.word_count = word_count;
    }

    public List<String> getWord_list() {
        return word_list;
    }

    public void setWord_list(List<String> word_list) {
        this.word_list = word_list;
    }

    public String getSuitage() {
        return suitage;
    }

    public void setSuitage(String suitage) {
        this.suitage = suitage;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
