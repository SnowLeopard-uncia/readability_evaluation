package com.example.bamboo.UI;

import static android.content.ContentValues.TAG;
import static android.widget.Toast.LENGTH_SHORT;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bamboo.R;
import com.example.bamboo.Util.HttpUtils;
import com.example.bamboo.Util.LrcParser;
import com.example.bamboo.fragment.ui.adapter.AudioListAdapter;
import com.example.bamboo.javaBean.Audio;
import com.example.bamboo.javaBean.BaseResponse;
import com.example.bamboo.javaBean.LrcInfo;
import com.example.bamboo.service.MusicService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AudioActivity extends BaseActivity implements View.OnClickListener{

    private String audioId;
    private String audioName;
    private String mp3Url;
    private String lrcUrl;
    private ImageView iv_play;
    private ImageView iv_last;
    private ImageView iv_next;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private Uri uri;
    private InputStream inputStream;
    private String lrcPath;
    private SeekBar musicSeekBar;
    private Timer timer =null;
    private  TimerTask timerTask=null;
    private String duration;
    private String singer;
    private int mPosition;
    private int mMaxPosition;
    private TextView tv_title;
    private TextView tv_singer;
    private TextView tv_duration;
    private TextView tv_level;
    private String lrcWord="";
    private TextView tv_audio_content;
    private TextView tv_audio_start_duration;
    private Intent mServiceIntent;
    private boolean isBindService;
    private MusicService.MusicBind musicBind;
    private int temp =0;
//    private int visibility;
    private ArrayList<Integer> mIsLockList;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        init();
        try {
            dataFromAudioList();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    private void initMediaPlayer(String url) throws IOException {
        if (url != null){
             uri = Uri.parse(url);
        }else{
            uri=Uri.parse("http://119.91.130.240/Englishmusic/A/Are you sleeping.mp3");
        }
        mediaPlayer.setDataSource(AudioActivity.this,uri); //指定url
        mediaPlayer.prepare();//进入准备状态

    }


    @SuppressLint("SetTextI18n")
    public void initTotalTime(){
        int time =mediaPlayer.getDuration();
        int second = time /1000;
        if (second >=10){
            tv_audio_start_duration.setText("00:" + second);
        }else {
            tv_audio_start_duration.setText("00:" +"0" + second);
        }
//        musicSeekBar.setMax(second);
    }


    private void dataFromAudioList() throws JSONException {
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        audioId=data.getString("musicSelectedID");
        audioName = data.getString("musicName");
        mPosition=data.getInt("position");
        mMaxPosition=data.getInt("maxPosition");
        mIsLockList=data.getIntegerArrayList("isLockList");
//        visibility=data.getInt("visibility");

        Log.e(TAG, "dataFromAudioList: "+mPosition +"   "+mMaxPosition);
        getDataFromResponse();
    }

    private void init() {
        initNavBar(true,"");
        iv_play=findViewById(R.id.iv_play);
        iv_last=findViewById(R.id.iv_last);
        iv_next=findViewById(R.id.iv_next);
        musicSeekBar =findViewById(R.id.musicSeekBar);
        tv_title=findViewById(R.id.tv_audio_title);
        tv_level=findViewById(R.id.tv_audio_level);
        tv_singer=findViewById(R.id.tv_singer);
        tv_duration=findViewById(R.id.tv_audio_duration);
tv_audio_content=findViewById(R.id.tv_audio_content);
        tv_audio_start_duration=findViewById(R.id.tv_audio_start_duration);

        iv_play.setOnClickListener(this);
        iv_last.setOnClickListener(this);
        iv_next.setOnClickListener(this);


        musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                if(i<60000){
                    if (i >=10000){
                        tv_audio_start_duration.setText("00:" + i/1000);
                    }else {
                        tv_audio_start_duration.setText("00:0" + i/1000);
                    }
                }else{
                    int sec = i/1000 %60;
                    if(sec<10){
                        tv_audio_start_duration.setText("0"+i/60000+":"+"0"+sec);
                    }else{
                        tv_audio_start_duration.setText("0"+i/60000+":"+sec);
                    }
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer.isPlaying()){
                    seekBar.setTag(!mediaPlayer.isPlaying());
                    iv_play.setActivated(false);
                    mediaPlayer.pause();
//此处逻辑要理清楚
                }else {
                    mediaPlayer.start();
                    mediaPlayer.pause();
                    seekBar.setTag(true);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int dest = seekBar.getProgress();
                int time = mediaPlayer.getDuration();
                int max = seekBar.getMax();
//                mediaPlayer.seekTo(time*dest/max);

                if (!(boolean)seekBar.getTag()){
                    iv_play.setActivated(true);
                    mediaPlayer.seekTo(seekBar.getProgress());
//                    mediaPlayer.seekTo(dest*1000);
                    mediaPlayer.start();
                    Log.i("MediaPlayer", "onStopTrackingTouch: " +mediaPlayer.getCurrentPosition());

                    timer = new Timer();
                    timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            if (mediaPlayer != null && temp==0) {
                                if (mediaPlayer.isPlaying()) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            musicSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                                        }
                                    });
                                }
                            }else{
                                if (mediaPlayer != null){
                                    mediaPlayer.pause();
//                                    mediaPlayer.reset();
//                                    mediaPlayer.stop();
//                                    mediaPlayer.release();
//                                    mediaPlayer=null;
                                }
                            }
                        }
                    };
                    timer.schedule(timerTask, 0, 1000);

                }else {
                    mediaPlayer.seekTo(seekBar.getProgress());
                }

            }
        });

    }
    private void getDataFromResponse() throws JSONException {
        Bmob.initialize(this, "f2c0e499b2961d0a3b7f5c8d52f3a264");
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        JSONObject params= new JSONObject();
        params.put("musicSelectedID", audioId);
        ace.callEndpoint("playMusic", params, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String result = object.toString();
                    try {
                        parseJsonDataWithGson(result);
                        Log.e(TAG, "audio done: "+result );
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });
    }

    private void parseJsonDataWithGson(String result) throws IOException {
        Gson gson = new Gson();
        BaseResponse<List<Audio>> response = gson.fromJson(result,new TypeToken<BaseResponse<List<Audio>>>(){}.getType());
        List<Audio> audioList = response.getResults();
        Audio audio = audioList.get(0);
        mp3Url=audio.getMp3Path();
        lrcUrl=audio.getLrcPath();
        audioName=audio.getName();
        tv_title.setText(audioName);
        tv_singer.setText(audio.getSinger());
        tv_duration.setText(audio.getDuration());
        tv_level.setText(audio.getLevel());
        Log.e(TAG, "parseJsonDataWithGson: "+mp3Url+"   "+lrcUrl);
        downloadLrc(lrcUrl);
        initMediaPlayer(mp3Url);
    }

    private void downloadLrc(String url) {
        HttpUtils.downloadLrc(url, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (Build.VERSION.SDK_INT >= 23) {
                    int REQUEST_CODE_CONTACT = 101;
                    String[] permissions = {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    //验证是否许可权限
                    for (String str : permissions) {
                        if (AudioActivity.this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                            //申请权限
                            AudioActivity.this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                            return;
                        } else {
                            //这里就是权限打开之后自己要操作的逻辑
                             inputStream = response.body().byteStream();
                            new DownTask().execute();


                        }
                    }
                }


            }

        });
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_play:
                assert mediaPlayer!=null;
                if(temp==0 && !mediaPlayer.isPlaying()){
//                    startMusicService();
                    iv_play.setActivated(true);
                    mediaPlayer.start(); //开始播放
                    musicSeekBar.setMax(mediaPlayer.getDuration());

                    if (timer != null) {
                        timer = null;
                        timerTask = null;
                    }
                    timer = new Timer();
                    timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            if (mediaPlayer != null && temp==0) {
                                if (mediaPlayer.isPlaying()) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            musicSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                                        }
                                    });
                                }
                            }else{
                                if (mediaPlayer != null){
                                    mediaPlayer.pause();
//                                    mediaPlayer.reset();
//                                    mediaPlayer.stop();
//                                    mediaPlayer.release();
//                                    mediaPlayer=null;
                                }

                            }
                        }
                    };
                    timer.schedule(timerTask, 0, 1000);
                }else{
                    iv_play.setActivated(false);
//                    musicBind.stopMusic();
                    mediaPlayer.pause();
                }
                break;


            case R.id.iv_last:
                mPosition=mPosition-1;
            if ((mPosition+1)==0){
                Toast.makeText(getApplicationContext(),"这是第一首", LENGTH_SHORT).show();
            }else if (mIsLockList.get(mPosition)==1){
                Toast.makeText(getApplicationContext(),"金币数量不足", LENGTH_SHORT).show();
                Log.e(TAG, "onClick: "+mPosition );
                for (int i = 0; i < mIsLockList.size(); i++) {
                    Log.e(TAG, "onClick: "+mIsLockList.get(i)+"   "+i );
                }
            }
            else{
                temp=1;
                if (mediaPlayer != null){
                    mediaPlayer.pause();
                    mediaPlayer.reset();
//                    mediaPlayer.stop();
//                    mediaPlayer.release();
//                    mediaPlayer=null;

                }
                try {
                    audioId=String.valueOf(Integer.parseInt(audioId)-1);
                    Log.e(TAG, "onClick: "+audioId);
                    getDataFromResponse();
                    temp=0;
                    iv_play.setActivated(false);
                    musicSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
                break;
            case R.id.iv_next:
                mPosition=mPosition+1;
                if (mPosition==(mMaxPosition-1)){
                    Toast.makeText(getApplicationContext(),"这是最后一首", LENGTH_SHORT).show();
                }else if (mIsLockList.get(mPosition)==1){
                    Toast.makeText(getApplicationContext(),"金币数量不足", LENGTH_SHORT).show();
                    Log.e(TAG, "onClick: "+mPosition );
                    for (int i = 0; i < mIsLockList.size(); i++) {
                        Log.e(TAG, "onClick: "+mIsLockList.get(i)+"   "+i );
                    }

                }
                else{

                    temp=1;
                    if (mediaPlayer != null){
                        mediaPlayer.pause();
                        mediaPlayer.reset();
//                        mediaPlayer.stop();
//                        mediaPlayer.release();
//                        mediaPlayer=null;
                    }
                    try {
                        audioId=String.valueOf(Integer.parseInt(audioId)+1);
                        Log.e(TAG, "onClick: "+audioId);
                        getDataFromResponse();
                        temp=0;
                        iv_play.setActivated(false);
                        musicSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    //如果不在销毁的时候释放资源，就会重放√
    @Override
    protected void onDestroy() {
        temp=1;
        if (mediaPlayer != null){
            mediaPlayer.reset();
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null; //设置为null释放内存
            //mediaplayer在播放中途没有播放完退出会IllegalStateExceptionat
            //stop called in state 1, mPlayer(0x0)
            //    error (-38, 0)
        }
        super.onDestroy();

    }

    @SuppressLint("StaticFieldLeak")
    class DownTask extends AsyncTask<Void,Integer,Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            FileOutputStream fileOutputStream = null;
            String filePath="";
            try {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//                    filePath=Environment.getExternalStorageDirectory().getAbsolutePath();
                    filePath=getApplicationContext().getExternalFilesDir(null).getAbsolutePath(); //解决了一个安卓11的问题！


                }else {
                    filePath=getFilesDir().getAbsolutePath();
                    filePath=getApplicationContext().getExternalFilesDir(null).getAbsolutePath();
                }

//                 File file = Context.getExternalFilesDir(null);
                File file = new File(filePath,audioName+".lrc");
                if (!file.exists()){
                    fileOutputStream=new FileOutputStream(file);
                    byte[] buffer = new byte[2048];
                    int len = 0 ;
                    while((len = inputStream.read(buffer)) != -1){
                        fileOutputStream.write(buffer,0,len);
                    }
                    fileOutputStream.flush();
                }
                lrcPath=filePath+"/"+audioName+".lrc";
                Log.e(TAG, "onResponse: "+"文件下载完成："+filePath+"   "+lrcPath );

                LrcParser lrcParser = new LrcParser();
                String path =lrcPath;
                try {
                    LrcInfo lrcInfo =lrcParser.parser(path);
//                    Log.e(TAG, "doInBackground: "+lrcInfo.getArtist());
                    lrcWord="";  //关键点
                    for (Object value:lrcInfo.getInfo().values()){
//                        Log.e(TAG, "doInBackground: value"+value);

                        lrcWord=lrcWord+value+"\n";
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_audio_content.setText(lrcWord);
//                            Toast.makeText(getApplicationContext(),"歌词换了", LENGTH_SHORT).show();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e(TAG, "onPreExecute: "+"下载开始" );
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Log.e(TAG, "onPostExecute: "+"下载结束" );
        }
    }
//启动音乐服务
    private void startMusicService(){
        //启动service
        if (mServiceIntent == null){
            mServiceIntent=new Intent(AudioActivity.this, MusicService.class);
            this.startService(mServiceIntent);
        }
        //绑定service
        if (!isBindService){
            isBindService=true;
            this.bindService(mServiceIntent,conn, Context.BIND_AUTO_CREATE);
        }
        //解除绑定 ， 如果绑定

    }
    public void destory(){
        if (isBindService){
            isBindService=false;
            this.unbindService(conn);
        }
    }
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            musicBind = (MusicService.MusicBind)iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

}