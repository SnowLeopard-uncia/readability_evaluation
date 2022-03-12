package com.snowleopard.bamboo.Util;

import static com.blankj.utilcode.util.ViewUtils.runOnUiThread;

import android.content.Context;
import android.os.Message;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.snowleopard.bamboo.javaBean.OCRResult;

import java.net.URLEncoder;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * 通用文字识别（高精度版）
 */
public class AccurateBasic {

    private static String accessToken;

    private static String result;



    public static String accurateBasic(String imagePath,String accessToken) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic";
        try {
            // 本地文件路径
            String filePath = imagePath;
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
//            String accessToken = AuthService.getAuth();
//            runOnUiThread(()->{
//                 accessToken = AuthServiceSafe.getAuth(ctx);
//
//            });
//            String accessToken = AuthServiceSafe.getAuth(ctx);


                 result = HttpUtil.post(url, accessToken, param);

            Log.e("AccurateBasic", "accurateBasic: " + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//        AccurateBasic.accurateBasic();
//    }

    /**
     *


    private static String parseJsonDataWithGson(String jsonData){
        Gson gson = new Gson();
        OCRResult ocrResult = gson.fromJson(jsonData,new TypeToken<OCRResult>() {
        }.getType());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ocrResult.getWords_result().size(); i++) {
            stringBuilder.append(ocrResult.getWords_result().get(i).toString());
        }
        return stringBuilder.toString();
    }
     */

    /*


    private void getToken(Context ctx) {
        OCR.getInstance(ctx).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                // 调用成功，返回AccessToken对象
                final String token = result.getAccessToken();

                Log.e("MainActivity", "MainActivity onResult()" + token);

                mToken = token;
            }

            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError子类SDKError对象
                error.printStackTrace();
            }
        }, getApplicationContext());

    }
     */
}