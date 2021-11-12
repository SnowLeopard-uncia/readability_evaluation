package com.example.bamboo.UI;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.bamboo.R;
import com.example.bamboo.javaBean.BaseResponse;
import com.example.bamboo.javaBean.UserLogin;
import com.example.bamboo.javaBean.UserRegister;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;
import cn.bmob.v3.listener.SaveListener;

public class ChooseLanguageActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager chooseViewPager;
    private List<View> mPages;
    private int[] imgArray;
    private Button btn_register;
    private List<UserRegister> userList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);
        init();
    }

    private void init() {
        chooseViewPager = findViewById(R.id.vp_choose);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        imgArray = new int[]{R.drawable.bg_choose_english, R.drawable.bg_choose_spanish};
        mPages = new ArrayList<View>();
        for (int i = 0; i < imgArray.length; i++) {
            ImageView imageView = new ImageView(this);
            mPages.add(imageView);
            imageView.setBackgroundResource(imgArray[i]);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                try {
                    doRegister();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    private void doRegister() throws JSONException {
        Bundle bundle = getIntent().getExtras();
        String phone = (String) bundle.get("mobilePhoneNumber");
        String password = (String) bundle.get("password");
        String name = (String) bundle.get("username");
        String grade = (String) bundle.get("level");

        Bmob.initialize(this, "f2c0e499b2961d0a3b7f5c8d52f3a264");
        String cloudCodeName = "userRegister";
        JSONObject params = new JSONObject();
        params.put("mobilePhoneNumber", phone);
        params.put("password", password);
        params.put("username", name);
        params.put("language", "English");
        params.put("level", grade);
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//第一个参数是云函数的方法名称，第二个参数是上传到云函数的参数列表（JSONObject cloudCodeParams）
        ace.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String result = object.toString();
                    Log.e(TAG, "注册done: json：" + result);
                    parseJsonDataWithGson(result);
                    saveUserID();
                    Toast.makeText(ChooseLanguageActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChooseLanguageActivity.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });
    }

    private void parseJsonDataWithGson(String jsonData) {
        Gson gson = new Gson();

        BaseResponse<List<UserRegister>> responseUserRegisterList = gson.fromJson(jsonData,
                new TypeToken<BaseResponse<List<UserRegister>>>() {
                }.getType());
        List<UserRegister> dataResponseList = responseUserRegisterList.getResults();
        for (UserRegister userRegister : dataResponseList) {
            userList.add(userRegister);

        }

    }

    private void saveUserID(){
        SharedPreferences.Editor editor = getSharedPreferences("userInformation",MODE_PRIVATE).edit();
        editor.putString("userID",userList.get(0).getObjectId());
        editor.apply();
    }
}
