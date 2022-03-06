package com.snowleopard.bamboo.Util;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.snowleopard.bamboo.javaBean.BaseResponse;
import com.snowleopard.bamboo.javaBean.UserRegister;

import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;

/**
 * 此类专用于Bmob后端数据的请求和结果处理
 * （这样就不用写这么多重复的了）
 */
public class BackEndInterfaceDataUtil {

    private static final String APP_KEY = "f2c0e499b2961d0a3b7f5c8d52f3a264";
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    /**
     *
     * @param context context，传入所在activity
     * @param cloudCodeName 所调用云函数的名字
     * @param params 云函数所需要的参数
     * @return 返回的结果，为json
     */
    public String requestData(Context context, String cloudCodeName, JSONObject params){
        Bmob.initialize(context, APP_KEY);
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
        //第一个参数是云函数的方法名称，第二个参数是上传到云函数的参数列表（JSONObject cloudCodeParams）
        ace.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                     result = object.toString();
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });
        return result;
    }


}
