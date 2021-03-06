package com.snowleopard.bamboo.UI;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.snowleopard.bamboo.R;
import com.snowleopard.bamboo.javaBean.BaseResponse;
import com.snowleopard.bamboo.javaBean.BookIntroduction;
import com.snowleopard.bamboo.javaBean.UserLocal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;

public class BookIntroductionActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_book;
    private TextView tv_book_title;
    private TextView tv_author;
    private TextView tv_level;
    private TextView tv_suit_age;
    private TextView tv_word_count;
    private TextView tv_SYN_ParseTreeHeight;
    private TextView tv_Word_CTTR;
    private Button btn_word_preview;
    private Button btn_reading_comprehension;
    private Button btn_reading;

    private Integer book_id;
    private Integer gold_coin;

    private List<BookIntroduction> bookList = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_introduction);

        init();
        initNavBar(true, "");

        book_id = getIntent().getExtras().getInt("book_id");
        gold_coin = getIntent().getExtras().getInt("gold_coin");

        UserLocal userLocal = LitePal.findFirst(UserLocal.class);
        if (userLocal.getLanguage().equals("English")){
            try {
                getResponseDataAboutEnglish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            try {
                getResponseDataAboutSpanish();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }

    private void init() {
        iv_book = findViewById(R.id.iv_book);
        tv_book_title = findViewById(R.id.tv_book_title);
        tv_author = findViewById(R.id.tv_author);
        tv_level = findViewById(R.id.tv_level);
        tv_suit_age = findViewById(R.id.tv_suit_age);
        tv_word_count = findViewById(R.id.tv_word_count);
        tv_SYN_ParseTreeHeight = findViewById(R.id.tv_SYN_ParseTreeHeight);
        tv_Word_CTTR = findViewById(R.id.tv_Word_CTTR);
        btn_word_preview = findViewById(R.id.btn_word_preview);
        btn_reading_comprehension = findViewById(R.id.btn_reading_comprehension);
        btn_reading = findViewById(R.id.btn_reading);
        btn_word_preview.setOnClickListener(this);
        btn_reading_comprehension.setOnClickListener(this);
        btn_reading.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_word_preview:
                Intent intent1 = new Intent(BookIntroductionActivity.this, WordPreviewActivity.class);
                intent1.putExtra("book_id", book_id);
                startActivity(intent1);
                break;
            case R.id.btn_reading_comprehension:
                Intent intent2 = new Intent(BookIntroductionActivity.this, ReadingComprehensionActivity.class);
                intent2.putExtra("book_id", book_id);
                intent2.putExtra("gold_coin", gold_coin);
                intent2.putExtra("book_objectId", bookList.get(0).getObjectId());
                startActivity(intent2);
                break;
            case R.id.btn_reading:
                Intent intent3 = new Intent(BookIntroductionActivity.this, BookReadingActivity.class);
                intent3.putExtra("book_id", book_id);
                startActivity(intent3);
                break;
            default:
                break;
        }
    }

    private void getResponseDataAboutEnglish() throws JSONException {
        Bmob.initialize(this, "f2c0e499b2961d0a3b7f5c8d52f3a264");
        String cloudCodeName = "selectBookInfo_byId";
        JSONObject params = new JSONObject();
        params.put("book_id", book_id);
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//???????????????????????????????????????????????????????????????????????????????????????????????????JSONObject cloudCodeParams???
        ace.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String responseData = object.toString();
                    parseJsonDataAboutEnglish(responseData);
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });

    }


    private void parseJsonDataAboutEnglish(String jsonData) {
        Gson gson = new Gson();

        BaseResponse<List<BookIntroduction>> responseBookIntroductionList = gson.fromJson(jsonData,
                new TypeToken<BaseResponse<List<BookIntroduction>>>() {
                }.getType());
        List<BookIntroduction> dataResponseList = responseBookIntroductionList.getResults();
        bookList.clear();
        for (BookIntroduction bookIntroduction : dataResponseList) {
            bookList.add(bookIntroduction);

            tv_book_title.setText("?????????" + bookIntroduction.getBook_title());
            tv_author.setText("?????????" + bookIntroduction.getAuthor());
            tv_level.setText("?????????" + bookIntroduction.getLevel());
            tv_suit_age.setText("???????????????" + bookIntroduction.getSuit_age());
            tv_word_count.setText("???????????????" + bookIntroduction.getWord_count());
            tv_SYN_ParseTreeHeight.setText("??????????????????" + bookIntroduction.getSYN_ParseTreeHeight());
            tv_Word_CTTR.setText("CTTR?????????" + bookIntroduction.getWord_CTTR());
            Glide.with(this).load(bookIntroduction.getCover_url()).into(iv_book);
        }

    }

    private void getResponseDataAboutSpanish() throws JSONException {
        Bmob.initialize(this, "f2c0e499b2961d0a3b7f5c8d52f3a264");
        String cloudCodeName = "selectSpanishBookInfo_byId";
        JSONObject params = new JSONObject();
        params.put("book_id", book_id);
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//???????????????????????????????????????????????????????????????????????????????????????????????????JSONObject cloudCodeParams???
        ace.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {

                    String responseData = object.toString();
                    parseJsonDataAboutSpanish(responseData);
                    Log.e("BookIntroduction", "done: "+responseData);
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });

    }

    private void parseJsonDataAboutSpanish(String jsonData) {
        Gson gson = new Gson();

        BaseResponse<List<BookIntroduction>> responseBookIntroductionList = gson.fromJson(jsonData,
                new TypeToken<BaseResponse<List<BookIntroduction>>>() {
                }.getType());
        List<BookIntroduction> dataResponseList = responseBookIntroductionList.getResults();
        bookList.clear();
        for (BookIntroduction bookIntroduction : dataResponseList) {
            bookList.add(bookIntroduction);

            tv_book_title.setText("?????????" + bookIntroduction.getTitle());
            tv_author.setText("?????????" + bookIntroduction.getWriter());
            tv_level.setText("?????????" + bookIntroduction.getLevel());
            tv_suit_age.setText("???????????????" + bookIntroduction.getSuitage());
//            tv_word_count.setText("???????????????" + bookIntroduction.getWord_count());
//            tv_SYN_ParseTreeHeight.setText("??????????????????" + bookIntroduction.getSYN_ParseTreeHeight());
//            tv_Word_CTTR.setText("CTTR?????????" + bookIntroduction.getWord_CTTR());
            tv_word_count.setText("?????????" + bookIntroduction.getTheme());
            Glide.with(this).load(bookIntroduction.getCover_url()).into(iv_book);
        }

    }


}