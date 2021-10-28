package com.example.bamboo.UI;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.example.bamboo.R;
import androidx.appcompat.app.AppCompatActivity;



public class LoginActivity extends AppCompatActivity {

    EditText et_user;
    EditText et_password;
    Button btn_login;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 去掉标题栏
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        et_user = findViewById(R.id.et_user);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        checkBox = findViewById(R.id.checkbox);

        // 控制用户名、密码图标大小
        Drawable drawable1 = getResources().getDrawable(R.drawable.user_icon); //获取图片
        drawable1.setBounds(50, 2, 125, 83);  //设置图片参数
        et_user.setCompoundDrawables(drawable1,null,null,null);
        Drawable drawable2 = getResources().getDrawable(R.drawable.password_icon); //获取图片
        drawable2.setBounds(50, 3, 122, 88);  //设置图片参数
        et_password.setCompoundDrawables(drawable2,null,null,null);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_user.getText())) {
                    Toast.makeText(LoginActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(et_password.getText())) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!checkBox.isChecked()){
                    Toast.makeText(LoginActivity.this, "请勾选用户协议", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}