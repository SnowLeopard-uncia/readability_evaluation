package com.snowleopard.bamboo.UI;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.snowleopard.bamboo.R;
import com.snowleopard.bamboo.Util.FileChooseUtil;
import com.snowleopard.bamboo.Util.HttpUtils;
import com.snowleopard.bamboo.javaBean.LevelText;
import com.snowleopard.bamboo.javaBean.TextResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FileLoadEvaluationActivity extends BaseActivity {

    private Button ibLoadFile;
    public static final String TXT="text/plain";
    public static final String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    String fileContent="";
    private TextView tvFileContent;
    private Button btnFileScore;
    private int temp =0;
    private String results="";
    private Bundle bundle = new Bundle();
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_load);
        initNavBar(true,getResources().getString(R.string.text_title));
        initView();
    }

    private void initView() {
        ibLoadFile=findViewById(R.id.ib_load_file);
        tvFileContent=findViewById(R.id.tv_file_content);
        btnFileScore=findViewById(R.id.btn_file_score);
        btnFileScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mText=tvFileContent.getText().toString();
                if (!mText.equals("")){
//                    startToEvaluate(mText, getString(R.string.TextUrl),getString(R.string.levelUrl));
                    goToEvaluatePart(mText);
                    Toast.makeText(getApplicationContext(),"测评中",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"文本为空，请重新输入",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ibLoadFile.setOnClickListener((view -> {

            if (Build.VERSION.SDK_INT >= 23) {
                int REQUEST_CODE_CONTACT = 101;
                String[] permissions = {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                //验证是否许可权限
                for (String str : permissions) {
                    if (FileLoadEvaluationActivity.this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                        //申请权限
                        FileLoadEvaluationActivity.this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                        return;
                    } else {
                        //这里就是权限打开之后自己要操作的逻辑
                        //打开文件选择器
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("*/*");
                        activityResultLauncher.launch(intent);
                    }
                }
            }

        }));
    }

    private void goToEvaluatePart(String mText) {
        Intent intent = new Intent(FileLoadEvaluationActivity.this,ResultEvaluationActivity.class);
        bundle.putString("text",mText);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void startToEvaluate(String mText, String textUrl, String levelUrl) {
        HttpUtils.readabilityWithOkhttp(textUrl, mText, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String responseBody=response.body().string();
                new Thread(()->{
                    String result = parseJsonDataWithGson(responseBody);
                    results=results+result;
                    bundle.putString("text",mText);
                    temp++;
                    Message msg =Message.obtain();
                    msg.what=temp;
                    handler.sendMessage(msg);
                }).start();
            }
        });

        HttpUtils.readabilityWithOkhttp(levelUrl, mText, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String responseBody=response.body().string();
                new Thread(()->{
                    Gson gson = new Gson();
                    LevelText levelText = gson.fromJson(responseBody, new TypeToken<LevelText>() {
                    }.getType());
                    results=results+levelText.toString();
                    temp++;
                    Message msg =Message.obtain();
                    msg.what=temp;
                    handler.sendMessage(msg);
                }).start();
            }
        });
    }
    private  String parseJsonDataWithGson(String jsonData) {
        Gson gson = new Gson();
        TextResponse textResponse = gson.fromJson(jsonData, new TypeToken<TextResponse>() {
        }.getType());

        String result =textResponse.getEnDF().toString()+
                textResponse.getEnGF().toString()+
                textResponse.getOSKF().toString()+
                textResponse.getPhrF().toString()+
                textResponse.getPOSF().toString()+
                textResponse.getPsyF().toString()+
                textResponse.getShaF().toString()+
                textResponse.getTraF().toString()+
                textResponse.getTrSF().toString()+
                textResponse.getTTRF().toString()+
                textResponse.getVarF().toString()+
                textResponse.getWBKF().toString()+
                textResponse.getWoKF().toString()+
                textResponse.getWoLF().toString()
                ;
        return result;

    }

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        //选择文件代码
                        String filePath = null;
                        Intent data = result.getData();
                        assert data != null;
                        Uri uri = data.getData();
                        filePath= FileChooseUtil.getInstance(getApplicationContext()).getChooseFileResultPath(uri);
              Log.d(TAG,"选择文件返回："+filePath);
                        try {
                           tvFileContent.setText( getFileContent(filePath));
                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(),"文件异常",Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
    );

    private String getFileContent(String filePath) throws IOException {
        StringBuilder contents = new StringBuilder();
        File file = new File(filePath);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this,"文件未找到",Toast.LENGTH_SHORT).show();
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = "";
        //分行读取
        while ((line = bufferedReader.readLine()) != null) {
            contents.append(line).append("\n");
        }
        assert inputStream != null;
        inputStream.close();//关闭输入流

        return contents.toString();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {      //判断标志位
                case 2:
                    Intent intent = new Intent(FileLoadEvaluationActivity.this,ResultEvaluationActivity.class);
                    bundle.putString("result",results);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
            }
        }
    };


}

//intent.setType(“image/*”);//选择图片
//        //intent.setType(“audio/*”); //选择音频
//        //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
//        //intent.setType(“video/*;image/*”);//同时选择视频和图片
//        intent.setType("*/*");//无类型限制