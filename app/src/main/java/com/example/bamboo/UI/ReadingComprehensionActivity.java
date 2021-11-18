package com.example.bamboo.UI;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bamboo.R;
import com.example.bamboo.javaBean.BaseResponse;
import com.example.bamboo.javaBean.BookIntroduction;
import com.example.bamboo.javaBean.QuizList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.login.LoginException;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;

public class ReadingComprehensionActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_question;
    private TextView tv_answerA;
    private TextView tv_answerB;
    private TextView tv_answerC;
    private TextView tv_answerD;
    private Button btn_answerA;
    private Button btn_answerB;
    private Button btn_answerC;
    private Button btn_answerD;
    private ImageView iv_toLeft;
    private ImageView iv_toRight;
    private ImageView iv_answerA;
    private ImageView iv_answerB;
    private ImageView iv_answerC;
    private ImageView iv_answerD;

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

        try {
            getResponseData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void init() {
        gold_coin = getIntent().getExtras().getInt("gold_coin");
        tv_question = findViewById(R.id.tv_question);
        tv_answerA = findViewById(R.id.tv_answerA);
        tv_answerB = findViewById(R.id.tv_answerB);
        tv_answerC = findViewById(R.id.tv_answerC);
        tv_answerD = findViewById(R.id.tv_answerD);
        btn_answerA = findViewById(R.id.btn_answerA);
        btn_answerB = findViewById(R.id.btn_answerB);
        btn_answerC = findViewById(R.id.btn_answerC);
        btn_answerD = findViewById(R.id.btn_answerD);
        iv_answerA = findViewById(R.id.iv_answerA);
        iv_answerB = findViewById(R.id.iv_answerB);
        iv_answerC = findViewById(R.id.iv_answerC);
        iv_answerD = findViewById(R.id.iv_answerD);
        iv_toLeft = findViewById(R.id.iv_toLeft);
        iv_toRight = findViewById(R.id.iv_toRight);
        iv_toLeft.setOnClickListener(this);
        iv_toRight.setOnClickListener(this);
        btn_answerA.setOnClickListener(this);
        btn_answerB.setOnClickListener(this);
        btn_answerC.setOnClickListener(this);
        btn_answerD.setOnClickListener(this);
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
            case R.id.btn_answerA:
//                correct = false;
                initButton();
                btn_answerA.setBackgroundResource(R.drawable.option_select);
                select = true;
                if ((mQuizList.get(question_num).getAnswer()).equals("a")) {
                    iv_answerA.setBackgroundResource(R.drawable.correct);
                    correct = true;
                } else {
                    iv_answerA.setBackgroundResource(R.drawable.incorrect);
                    correct = false;
                }

                break;
            case R.id.btn_answerB:
//                correct = false;
                initButton();
                btn_answerB.setBackgroundResource(R.drawable.option_select);
                select = true;
                if ((mQuizList.get(question_num).getAnswer()).equals("b")) {
                    iv_answerB.setBackgroundResource(R.drawable.correct);
                    correct = true;
                } else {
                    iv_answerB.setBackgroundResource(R.drawable.incorrect);
                    correct = false;
                }
                break;
            case R.id.btn_answerC:
//                correct = false;
                initButton();
                btn_answerC.setBackgroundResource(R.drawable.option_select);
                select = true;
                if ((mQuizList.get(question_num).getAnswer()).equals("c")) {
                    iv_answerC.setBackgroundResource(R.drawable.correct);
                    correct = true;
                } else {
                    iv_answerC.setBackgroundResource(R.drawable.incorrect);
                    correct = false;
                }
                break;
            case R.id.btn_answerD:
//                correct = false;
                initButton();
                btn_answerD.setBackgroundResource(R.drawable.option_select);
                select = true;
                if ((mQuizList.get(question_num).getAnswer()).equals("d")) {
                    iv_answerD.setBackgroundResource(R.drawable.correct);
                    correct = true;
                } else {
                    iv_answerD.setBackgroundResource(R.drawable.incorrect);
                    correct = false;
                }
                break;
            default:
                break;
        }
    }

    private void getResponseData() throws JSONException {
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
                    Log.e(TAG, "done: json：" + responseData);
                    parseJsonDataWithGson(responseData);
                } else {
                    Log.e(TAG, " " + e.getMessage());
                }
            }
        });

    }


    private void parseJsonDataWithGson(String jsonData) {
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
        tv_question.setText(mQuizList.get(question_num).getQuestion());
        tv_answerA.setText(mQuizList.get(question_num).getChoice_obj().getA());
        tv_answerB.setText(mQuizList.get(question_num).getChoice_obj().getB());
        tv_answerC.setText(mQuizList.get(question_num).getChoice_obj().getC());
        tv_answerD.setText(mQuizList.get(question_num).getChoice_obj().getD());
    }

    private void initButton() {
        btn_answerA.setBackgroundResource(R.drawable.option_unselect);
        btn_answerB.setBackgroundResource(R.drawable.option_unselect);
        btn_answerC.setBackgroundResource(R.drawable.option_unselect);
        btn_answerD.setBackgroundResource(R.drawable.option_unselect);
        iv_answerA.setBackgroundResource(R.color.transparent);
        iv_answerB.setBackgroundResource(R.color.transparent);
        iv_answerC.setBackgroundResource(R.color.transparent);
        iv_answerD.setBackgroundResource(R.color.transparent);
        select = false;
    }

//    private void delay(){
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//
//            }
//        };
//        Timer timer = new Timer();
//        timer.schedule(task, 1000);//1秒后执行TimeTask的run方法
//
//    }
}