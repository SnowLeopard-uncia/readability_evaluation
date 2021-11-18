package com.example.bamboo.Util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;
import com.blankj.utilcode.util.RegexUtils;



public class UserUtils {
    // 检验手机号合法性
    public static boolean validateLogin(Context context, String phone){
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(context, "手机号不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!RegexUtils.isMobileExact(phone)){
            Toast.makeText(context, "无效手机号",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }
}