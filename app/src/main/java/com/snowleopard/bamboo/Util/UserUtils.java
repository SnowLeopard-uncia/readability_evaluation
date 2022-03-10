package com.snowleopard.bamboo.Util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;
import com.blankj.utilcode.util.RegexUtils;



public class UserUtils {
    // 检验用户名合法性
    public static boolean validateLogin(Context context, String username){
        if(TextUtils.isEmpty(username)){
            Toast.makeText(context, "用户名不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(username.length()>10){
            Toast.makeText(context, "用户名长度不能大于10位",Toast.LENGTH_SHORT).show();
            return false;
        }
//        if(!RegexUtils.isMobileExact(username)){
//            Toast.makeText(context, "无效手机号",Toast.LENGTH_SHORT).show();
//            return false;
//        }
        return true;

    }
}