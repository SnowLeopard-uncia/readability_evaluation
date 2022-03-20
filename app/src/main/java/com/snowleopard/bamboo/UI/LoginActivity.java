package com.snowleopard.bamboo.UI;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.snowleopard.bamboo.R;
import com.snowleopard.bamboo.Util.MD5Cypher;
import com.snowleopard.bamboo.javaBean.BaseResponse;
import com.snowleopard.bamboo.javaBean.UserLogin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.snowleopard.bamboo.view.InputView;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;


public class LoginActivity extends BaseActivity implements View.OnClickListener{
    InputView etUsername;
    InputView etPassword;

    Button btnLogin;
    TextView tvRegister;
    CheckBox rbRememberPwd;
    CheckBox cbLoginHint;
    private List<UserLogin> userList = new ArrayList<>();


    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cbLoginHint=findViewById(R.id.cb_login_hint);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_register);
        rbRememberPwd =findViewById(R.id.rb_remember_pwd);
//        checkBox = findViewById(R.id.checkbox);

        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);

//        et_username.setText("hello");
        rememberPwd();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                try {
                    doLogin();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
                break;

            case R.id.tv_register:
                Intent intent2 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent2);
                break;

            default:
                break;
        }
    }

//取出密码
    public void rememberPwd(){
//        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
//        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences pref = getSharedPreferences("userinfo",MODE_PRIVATE);
        Boolean isRemember = pref.getBoolean("isRemember",false);
        if (isRemember){
            String userName = pref.getString("userName","");
            String password = pref.getString("userPwd","");
            etUsername.setText(userName);
            etPassword.setText(password);
            rbRememberPwd.setChecked(true);
        }

    }

//登录成功之后才能执行这个记住密码的方法(存入)
    public void savePwd(String password){
        String userID = userList.get(0).getObjectId();
        SharedPreferences.Editor editor = getSharedPreferences("userinfo",MODE_PRIVATE).edit();
        editor.putString("userId",userID);
        editor.putString("userName", etUsername.getInputStr().trim());
        //检查复选框是否选择
        if(rbRememberPwd.isChecked()){
          //  String password=et_password.getInputStr().trim();
            editor.putBoolean("isRemember",true);
            editor.putString("userPwd",password);

        }else {
            editor.clear();
        }
        editor.apply();
    }

    //判断文件是否存在
    private boolean fileIsExists(String filePath)
    {
        try
        {
            File f = new File(filePath);
            if(!f.exists())
            {
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }
    public void doLogin() throws JSONException {
        String userName = etUsername.getInputStr().trim();
        String password = etPassword.getInputStr().trim();

        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this, "用户名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!cbLoginHint.isChecked()) {
            Toast.makeText(this, "请勾选用户协议", Toast.LENGTH_SHORT).show();
            return;
        }
        String sendPassword="";
        if(fileIsExists("/data/data/com.snowleopard.bamboo/shared_prefs/userinfo.xml")){
            SharedPreferences pref = getSharedPreferences("userinfo",MODE_PRIVATE);
            String name = pref.getString("userName","0");
            if (name.equals("0") || !name.equals(userName)){
                sendPassword=MD5Cypher.md5(password+MD5Cypher.md5(password));
            }
            else {
                if (pref.getBoolean("isRemember",false)){
                    sendPassword=pref.getString("userPwd","");
                }else{
                    sendPassword=MD5Cypher.md5(password+MD5Cypher.md5(password));
                }

            }
        }else {
            sendPassword=MD5Cypher.md5(password+MD5Cypher.md5(password));
        }


        Bmob.initialize(this, "f2c0e499b2961d0a3b7f5c8d52f3a264");
//        String cloudCodeName = "userLogin";
                String cloudCodeName = "userLogin";
        JSONObject params = new JSONObject();
        params.put("username", userName);
        params.put("password", sendPassword);
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//第一个参数是云函数的方法名称，第二个参数是上传到云函数的参数列表（JSONObject cloudCodeParams）
        String finalSendPassword = sendPassword;
        ace.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String result = object.toString();
                    Log.e(TAG, "登录：" + result);

                    if (result.equals("2")) {
                        Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    } else {
                        parseJsonDataWithGson(result);
                        savePwd(finalSendPassword);
                   //     saveUserID();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        intent.putExtra("userID", userList.get(0).getObjectId());
                        Log.e(TAG, "登录时获得用户ID： " + userList.get(0).getObjectId());
                        startActivity(intent);
                    }

                } else {
                    Log.e(TAG, " " + e.getMessage());
                    Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
   }


    private void parseJsonDataWithGson(String jsonData) {
        Gson gson = new Gson();

        BaseResponse<List<UserLogin>> responseUserLoginList = gson.fromJson(jsonData,
                new TypeToken<BaseResponse<List<UserLogin>>>() {
                }.getType());
        List<UserLogin> dataResponseList = responseUserLoginList.getResults();
        for (UserLogin userLogin : dataResponseList) {
            userList.add(userLogin);
            Log.e(TAG, "金币：" + userLogin.getCoin());

        }

    }
    private void saveUserID(){
        SharedPreferences.Editor editor = getSharedPreferences("userinfo",MODE_PRIVATE).edit();
        editor.putString("userId",userList.get(0).getObjectId());
        editor.apply();
    }

}