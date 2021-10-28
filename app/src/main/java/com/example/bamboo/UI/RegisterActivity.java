package com.example.bamboo.UI;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.bamboo.R;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_name;
    private TextView tv_grade;
    private Spinner spinner;
    private Button btn_language_selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 去掉标题栏
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        et_name = findViewById(R.id.et_name);
        tv_grade = findViewById(R.id.tv_grade);
        spinner = findViewById(R.id.spinner);
        btn_language_selection = findViewById(R.id.btn_language_selection);

        // 控制用户名、密码图标大小
        Drawable drawable1 = getResources().getDrawable(R.drawable.name_icon);
        drawable1.setBounds(50, 0, 135, 105);
        et_name.setCompoundDrawables(drawable1,null,null,null);
        Drawable drawable2 = getResources().getDrawable(R.drawable.grade_icon);
        drawable2.setBounds(40, 0, 147, 118);
        tv_grade.setCompoundDrawables(drawable2,null,null,null);

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


        btn_language_selection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_name.getText())) {
                    Toast.makeText(RegisterActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

//                if (tv_grade.equals("           请选择年级") || tv_grade.equals("")) {
//                    Toast.makeText(InformationSelection.this, "年级不能为空", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                Intent intent = new Intent(RegisterActivity.this,LanguageSelection.class);
//                startActivity(intent);
            }
        });
    }
}