package com.example.bamboo.UI;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.bamboo.R;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import org.json.JSONObject;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;
import cn.bmob.v3.listener.SaveListener;


public class LoginActivity extends BaseActivity implements View.OnClickListener{
    EditText et_user;
    EditText et_password;
    Button btn_login;
    TextView tv_register;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        Bmob.initialize(this, "f2c0e499b2961d0a3b7f5c8d52f3a264"); //初始化Bmob功能

        et_user = findViewById(R.id.et_user);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        tv_register = findViewById(R.id.to_register_text);
        btn_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);

        // 控制用户名、密码图标大小
        Drawable drawable1 = getResources().getDrawable(R.drawable.phone);
        drawable1.setBounds(65, 0, 135, 110);
        et_user.setCompoundDrawables(drawable1,null,null,null);
        Drawable drawable2 = getResources().getDrawable(R.drawable.password_icon);
        drawable2.setBounds(50, 0, 135, 105);
        et_password.setCompoundDrawables(drawable2,null,null,null);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                Intent intent1 = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent1);
                break;

            case R.id.to_register_text:
                Intent intent2 = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    private void doLogin(){//登录
        String phone = et_user.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String cloudCodeName = "userLogin";
        JSONObject params = new JSONObject();
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//第一个参数是上下文对象，第二个参数是云函数的方法名称，第三个参数是上传到云函数的参数列表（JSONObject cloudCodeParams），第四个参数是回调类
        ace.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String result = object.toString();
                } else {
                    Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }



}