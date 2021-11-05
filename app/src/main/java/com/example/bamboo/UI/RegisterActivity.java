package com.example.bamboo.UI;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.bamboo.R;
import com.example.bamboo.javaBean.User;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity {

    EditText et_user;
    EditText et_password;
    EditText et_confirm_password;
    private EditText et_name;
    private TextView tv_grade;
    private Spinner spinner;
    private Button btn_register;
    CheckBox checkBox;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_user = findViewById(R.id.et_user);
        et_password = findViewById(R.id.et_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);
        et_name = findViewById(R.id.et_name);
        tv_grade = findViewById(R.id.tv_grade);
        spinner = findViewById(R.id.spinner);
        btn_register = findViewById(R.id.btn_register);
        checkBox = findViewById(R.id.checkbox);

        initView();


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RegisterActivity.this,ChooseLanguageActivity.class);
                startActivity(intent);
            }
        });
    }

    private void signUp(final View view) {
        String phone = et_user.getText().toString();
        String password = et_password.getText().toString();
        String confirmPassword = et_confirm_password.getText().toString();
        String name = et_name.getText().toString();
        String grade = tv_grade.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(RegisterActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(RegisterActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tv_grade.equals("           请选择年级") || tv_grade.equals("")) {
            Toast.makeText(RegisterActivity.this, "年级不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!checkBox.isChecked()) {
            Toast.makeText(RegisterActivity.this, "请勾选用户协议", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!confirmPassword.equals(password)){
            Toast.makeText(RegisterActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        final User user = new User();
        user.setMobilePhoneNumber(phone + System.currentTimeMillis());
        user.setPassword(password + System.currentTimeMillis());
        user.setUsername(name + System.currentTimeMillis());
        user.setLevel(grade + System.currentTimeMillis());
//        user.setLanguage(language + System.currentTimeMillis());

        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败：" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void initView(){
        Drawable drawable1 = getResources().getDrawable(R.drawable.phone);
        drawable1.setBounds(65, 0, 135, 110);
        et_user.setCompoundDrawables(drawable1,null,null,null);
        Drawable drawable2 = getResources().getDrawable(R.drawable.password_icon);
        drawable2.setBounds(50, 0, 135, 105);
        et_password.setCompoundDrawables(drawable2,null,null,null);
        Drawable drawable3 = getResources().getDrawable(R.drawable.name_icon);
        drawable3.setBounds(50, 0, 135, 105);
        et_name.setCompoundDrawables(drawable3,null,null,null);
        Drawable drawable4 = getResources().getDrawable(R.drawable.grade_icon);
        drawable4.setBounds(40, 0, 147, 118);
        tv_grade.setCompoundDrawables(drawable4,null,null,null);
        Drawable drawable5 = getResources().getDrawable(R.drawable.password_confirm);
        drawable5.setBounds(50, 0, 135, 105);
        et_confirm_password.setCompoundDrawables(drawable5,null,null,null);

        String[] arr = {"","幼儿园","一年级","二年级","三年级","四年级","五年级","六年级","初一","初二",
                "初三","高一","高二","高三"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    // 主要功能代码；
                    view.setVisibility(View.INVISIBLE);
                } else {
                    //获取到对象
                    String mGrade=adapter.getItem(i);
                    tv_grade.setText("           "+mGrade);
                    tv_grade.setTextColor(Color.parseColor("#000000"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

}