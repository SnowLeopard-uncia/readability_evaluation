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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.snowleopard.bamboo.R;
import com.snowleopard.bamboo.javaBean.BaseResponse;
import com.snowleopard.bamboo.javaBean.QuizList;
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

public class ReadingComprehensionActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout llAnswerA;
    private LinearLayout llAnswerB;
    private LinearLayout llAnswerC;
    private LinearLayout llAnswerD;

    private TextView tvQuestion;
    private TextView tvAnswerA;
    private TextView tvAnswerB;
    private TextView tvAnswerC;
    private TextView tvAnswerD;
    private Button btnAnswerA;
    private Button btnAnswerB;
    private Button btnAnswerC;
    private Button btnAnswerD;
    
    private ImageView ivToLeft;
    private ImageView ivToRight;
    private Button btnAnswerACheck;
    private Button btnAnswerBCheck;
    private Button btnAnswerCCheck;
    private Button btnAnswerDCheck;

    private int question_num = 0;
    private boolean correct = false;
    private boolean select = false;
    private List<QuizList> mQuizList = new ArrayList<>();

    private Integer gold_coin;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_comprehension);

        initNavBar(true, getResources().getString(R.string.reading_comprehension));
        init();

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
        gold_coin = getIntent().getExtras().getInt("gold_coin");
        tvQuestion = findViewById(R.id.tv_question);
        tvAnswerA = findViewById(R.id.tv_answerA);
        tvAnswerB = findViewById(R.id.tv_answerB);
        tvAnswerC = findViewById(R.id.tv_answerC);
        tvAnswerD = findViewById(R.id.tv_answerD);
        btnAnswerA = findViewById(R.id.btn_answerA);
        btnAnswerB = findViewById(R.id.btn_answerB);
        btnAnswerC = findViewById(R.id.btn_answerC);
        btnAnswerD = findViewById(R.id.btn_answerD);
        btnAnswerACheck = findViewById(R.id.iv_answerA);
        btnAnswerBCheck = findViewById(R.id.iv_answerB);
        btnAnswerCCheck = findViewById(R.id.iv_answerC);
        btnAnswerDCheck = findViewById(R.id.iv_answerD);
        ivToLeft = findViewById(R.id.iv_toLeft);
        ivToRight = findViewById(R.id.iv_toRight);
        llAnswerA=findViewById(R.id.ll_answerA);
        llAnswerB=findViewById(R.id.ll_answerB);
        llAnswerC=findViewById(R.id.ll_answerC);
        llAnswerD=findViewById(R.id.ll_answerD);
        ivToLeft.setOnClickListener(this);
        ivToRight.setOnClickListener(this);
        llAnswerA.setOnClickListener(this);
        llAnswerB.setOnClickListener(this);
        llAnswerC.setOnClickListener(this);
        llAnswerD.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toLeft:
                initButton();
                if (question_num > 0) {
                    question_num--;
                    initQuestion();
                } else {
                    Toast.makeText(this, "这是第一个问题", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_toRight:
                if((!correct) || (!select)) {
                    Toast.makeText(this, "请先正确回答这个问题", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (correct && question_num == mQuizList.size() - 1) {
                    Intent intent = new Intent(ReadingComprehensionActivity.this, ReadingFinishingActivity.class);
                    intent.putExtra("gold_coin", gold_coin);
                    intent.putExtra("book_objectId", getIntent().getExtras().getString("book_objectId"));
                    startActivity(intent);
                    finish();
                }
                if (correct && question_num < mQuizList.size() - 1) {
                    question_num++;
                    initQuestion();
                    initButton();
                    return;
                }

                break;
            case R.id.ll_answerA:
//                correct = false;
                initButton();
                btnAnswerA.setBackgroundResource(R.drawable.option_select);
                select = true;
                if ((mQuizList.get(question_num).getAnswer()).equals("a") ||
                        (mQuizList.get(question_num).getAnswer()).equals("A")) {
                    btnAnswerACheck.setBackgroundResource(R.drawable.correct);
                    correct = true;
                } else {
                    btnAnswerACheck.setBackgroundResource(R.drawable.incorrect);
                    correct = false;
                }

                break;
            case R.id.ll_answerB:
//                correct = false;
                initButton();
                btnAnswerB.setBackgroundResource(R.drawable.option_select);
                select = true;
                if ((mQuizList.get(question_num).getAnswer()).equals("b") ||
                        (mQuizList.get(question_num).getAnswer()).equals("B")) {
                    btnAnswerBCheck.setBackgroundResource(R.drawable.correct);
                    correct = true;
                } else {
                    btnAnswerBCheck.setBackgroundResource(R.drawable.incorrect);
                    correct = false;
                }

                break;
            case R.id.ll_answerC:
//                correct = false;
                initButton();

                btnAnswerC.setBackgroundResource(R.drawable.option_select);
                select = true;
                if ((mQuizList.get(question_num).getAnswer()).equals("c") ||
                        (mQuizList.get(question_num).getAnswer()).equals("C")) {
                    btnAnswerCCheck.setBackgroundResource(R.drawable.correct);
                    correct = true;
                } else {
                    btnAnswerCCheck.setBackgroundResource(R.drawable.incorrect);
                    correct = false;
                }
                break;

            case R.id.ll_answerD:
//                correct = false;
                initButton();
                btnAnswerD.setBackgroundResource(R.drawable.option_select);
                select = true;
                if ((mQuizList.get(question_num).getAnswer()).equals("d") ||
                        (mQuizList.get(question_num).getAnswer()).equals("D")) {
                    btnAnswerDCheck.setBackgroundResource(R.drawable.correct);
                    correct = true;
                } else {
                    btnAnswerDCheck.setBackgroundResource(R.drawable.incorrect);
                    correct = false;
                }
                break;
            default:
                break;
        }
    }

    private void getResponseDataAboutEnglish() throws JSONException {
        Bmob.initialize(this, "f2c0e499b2961d0a3b7f5c8d52f3a264");
        String cloudCodeName = "selectQuizInfo_byBookId";
        JSONObject params = new JSONObject();
        Integer book_id = getIntent().getExtras().getInt("book_id");
        params.put("book_id", book_id);
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//第一个参数是云函数的方法名称，第二个参数是上传到云函数的参数列表（JSONObject cloudCodeParams）
        ace.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String responseData = object.toString();
                    Log.e("ReadingComprehension", "done: "+responseData );
                    parseJsonDataAboutEnglish(responseData);
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });

    }


    private void parseJsonDataAboutEnglish(String jsonData) {
        Gson gson = new Gson();

        BaseResponse<List<QuizList>> responseQuizList = gson.fromJson(jsonData,
                new TypeToken<BaseResponse<List<QuizList>>>() {
                }.getType());
        List<QuizList> dataResponseList = responseQuizList.getResults();
        for (QuizList quizList : dataResponseList) {
            mQuizList.add(quizList);

        }
        initQuestion();

    }

    private void getResponseDataAboutSpanish() throws JSONException {
        Bmob.initialize(this, "f2c0e499b2961d0a3b7f5c8d52f3a264");
        String cloudCodeName = "selectSpanishQuizInfo_byBookId";
        JSONObject params = new JSONObject();
        Integer book_id = getIntent().getExtras().getInt("book_id");
        params.put("book_id", book_id);
        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
//第一个参数是云函数的方法名称，第二个参数是上传到云函数的参数列表（JSONObject cloudCodeParams）
        ace.callEndpoint(cloudCodeName, params, new CloudCodeListener() {
            @Override
            public void done(Object object, BmobException e) {
                if (e == null) {
                    String responseData = object.toString();
                    Log.e(TAG, "done: json：" + responseData);
                    parseJsonDataAboutSpanish(responseData);
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });

    }


    private void parseJsonDataAboutSpanish(String jsonData) {
        Gson gson = new Gson();

        BaseResponse<List<QuizList>> responseQuizList = gson.fromJson(jsonData,
                new TypeToken<BaseResponse<List<QuizList>>>() {
                }.getType());
        List<QuizList> dataResponseList = responseQuizList.getResults();
        for (QuizList quizList : dataResponseList) {
            mQuizList.add(quizList);

        }
        initQuestion();

    }


    private void initQuestion() {
        UserLocal userLocal = LitePal.findFirst(UserLocal.class);
        if (userLocal.getLanguage().equals("English")){
            tvQuestion.setText(mQuizList.get(question_num).getQuestion());
            tvAnswerA.setText(mQuizList.get(question_num).getChoice_obj().getA());
            tvAnswerB.setText(mQuizList.get(question_num).getChoice_obj().getB());
            tvAnswerC.setText(mQuizList.get(question_num).getChoice_obj().getC());
            tvAnswerD.setText(mQuizList.get(question_num).getChoice_obj().getD());
        }else{
            tvQuestion.setText(mQuizList.get(question_num).getQuestion());
            tvAnswerA.setText(mQuizList.get(question_num).getChoice().getA());
            tvAnswerB.setText(mQuizList.get(question_num).getChoice().getB());
            tvAnswerC.setText(mQuizList.get(question_num).getChoice().getC());
            tvAnswerD.setText(mQuizList.get(question_num).getChoice().getD());

        }
    }

    private void initButton() {
        btnAnswerA.setBackgroundResource(R.drawable.option_unselect);
        btnAnswerB.setBackgroundResource(R.drawable.option_unselect);
        btnAnswerC.setBackgroundResource(R.drawable.option_unselect);
        btnAnswerD.setBackgroundResource(R.drawable.option_unselect);
        btnAnswerACheck.setBackgroundResource(R.color.transparent);
        btnAnswerBCheck.setBackgroundResource(R.color.transparent);
        btnAnswerCCheck.setBackgroundResource(R.color.transparent);
        btnAnswerDCheck.setBackgroundResource(R.color.transparent);
        select = false;
    }


}