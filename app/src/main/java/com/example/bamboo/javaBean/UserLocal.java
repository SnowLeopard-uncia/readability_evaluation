package com.example.bamboo.javaBean;

import org.litepal.crud.LitePalSupport;

public class UserLocal extends LitePalSupport {
    private Integer coin;
    private String level;

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
