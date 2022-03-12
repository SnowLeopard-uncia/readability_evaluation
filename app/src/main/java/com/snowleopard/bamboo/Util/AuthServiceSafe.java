package com.snowleopard.bamboo.Util;

import static com.blankj.utilcode.util.ViewUtils.runOnUiThread;

import android.content.Context;
import android.util.Log;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;

public class AuthServiceSafe {

    private static String mToken;

    public static String getAuth(Context ctx){
        Log.e("AuthServiceSafe", "getAuth");

        OCR.getInstance(ctx).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                // 调用成功，返回AccessToken对象
                final String token = result.getAccessToken();

                Log.e("AuthServiceSafe", "AuthServiceSafe getAuth()" + token);

                mToken = token;

            }

            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError子类SDKError对象
                error.printStackTrace();
            }
        }, ctx.getApplicationContext());

        return mToken;
    }
}
