package com.snowleopard.bamboo.UI;



import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.snowleopard.bamboo.R;
import com.snowleopard.bamboo.Util.BackEndInterfaceDataUtil;
import com.snowleopard.bamboo.Util.MD5Cypher;
import com.snowleopard.bamboo.Util.UserUtils;
import com.snowleopard.bamboo.javaBean.BaseResponse;
import com.snowleopard.bamboo.javaBean.UserRegister;
import com.snowleopard.bamboo.view.InputView;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    EditText et_user;
    EditText et_password;
    EditText et_confirm_password;
    private List<UserRegister> userList = new ArrayList<>();

    private Button btn_register;

    String[] arr = {"", "幼儿园", "一年级", "二年级", "三年级", "四年级", "五年级", "六年级", "初一", "初二",
            "初三", "高一", "高二", "高三"};
    String[] grade = {"", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"};

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initNavBar(true,getResources().getString(R.string.register));
        initView();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                String username = et_user.getText().toString();
                String password = et_password.getText().toString();
                password= MD5Cypher.md5(password+MD5Cypher.md5(password));
                String confirmPassword = et_confirm_password.getText().toString();
                confirmPassword=MD5Cypher.md5(confirmPassword+MD5Cypher.md5(confirmPassword));
                // 验证用户输入是否合法
                if (!UserUtils.validateLogin(this, username)){
                    return ;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!confirmPassword.equals(password)) {
                    Toast.makeText(RegisterActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }

                /*
                 *
                Intent intent = new Intent(RegisterActivity.this, ChooseLanguageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("mobilePhoneNumber", et_user.getText() + "");
                bundle.putString("password", et_password.getText() + "");
//                bundle.putString("nickname", et_name.getText() + "");
//                bundle.putString("level", level + "");
                intent.putExtras(bundle);
                startActivity(intent);
                 */

                Bmob.initialize(this, "f2c0e499b2961d0a3b7f5c8d52f3a264");
//        String cloudCodeName = "userLogin";
                String cloudCodeName = "userRegister";
                JSONObject params = new JSONObject();
                try {
                    params.put("username", username);
                    params.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//第一个参数是云函数的方法名称，第二个参数是上传到云函数的参数列表（JSONObject cloudCodeParams）
                ace.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
                    @Override
                    public void done(Object object, BmobException e) {
                        if (e == null) {
                            String result = object.toString();
                            if (result.equals("1")){
                                Toast.makeText(RegisterActivity.this,"用户名已存在",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                parseJsonDataWithGson(result);
                                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                saveUserID();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                            Log.e("RegisterActivity：", "RegisterActivity：" + result);
                        } else {
                            Log.e(TAG, " " + e.getMessage());
                            Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//                saveUserID();

                break;
            default:
                break;
        }
    }

    private void parseJsonDataWithGson(String jsonData) {
        Gson gson = new Gson();
        BaseResponse<List<UserRegister>> responseUserRegister = gson.fromJson(jsonData,
                new TypeToken<BaseResponse<List<UserRegister>>>() {
                }.getType());
        List<UserRegister> dataResponse =responseUserRegister.getResults();
        userList.add(dataResponse.get(0));
        Log.e("RegisterActivity", "parseJsonDataWithGson: "+dataResponse.get(0).getImageHead());
//
    }

    private void saveUserID(){
        SharedPreferences.Editor editor = getSharedPreferences("userinfo",MODE_PRIVATE).edit();
        editor.putString("userId",userList.get(0).getObjectId());
        editor.apply();
    }

    private void initView() {
        et_user = findViewById(R.id.et_username_register);
        et_password = findViewById(R.id.et_password_register);
        et_confirm_password = findViewById(R.id.et_confirm_password);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

    }


}