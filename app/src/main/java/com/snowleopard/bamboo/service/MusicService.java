package com.snowleopard.bamboo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MusicService extends Service {
    //有个对象
    public MusicService() {
    }

    public class MusicBind extends Binder{
        //设置音乐的方法
        public void setMusic(){

        }
        //播放音乐
        public void playMusic(){

        }
        //暂停播放
        public void stopMusic(){

        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBind();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化对象
    }
}