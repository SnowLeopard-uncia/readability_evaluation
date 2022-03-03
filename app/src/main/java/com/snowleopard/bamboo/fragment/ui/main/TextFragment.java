package com.snowleopard.bamboo.fragment.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.snowleopard.bamboo.R;
import com.snowleopard.bamboo.UI.FileLoadEvaluationActivity;
import com.snowleopard.bamboo.UI.PhotoLoadEvaluationActivity;
import com.snowleopard.bamboo.UI.TextTextEvaluationActivity;

public class TextFragment extends Fragment implements View.OnClickListener{
    private LinearLayout textLoad;
    private LinearLayout photoLoad;
    private LinearLayout fileLoad;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        textLoad=getView().findViewById(R.id.linearLayout_load_text);
        photoLoad=getView().findViewById(R.id.linearLayout_load_photo);
        fileLoad=getView().findViewById(R.id.linearLayout_load_file);

        textLoad.setOnClickListener(this);
        photoLoad.setOnClickListener(this);
        fileLoad.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
    switch (view.getId()){
        case R.id.linearLayout_load_text:
            Intent intentText = new Intent(getActivity(), TextTextEvaluationActivity.class);
            startActivity(intentText);
            break;
        case R.id.linearLayout_load_photo:
            Intent intentPhoto = new Intent(getActivity(), PhotoLoadEvaluationActivity.class);
            startActivity(intentPhoto);
            break;
        case R.id.linearLayout_load_file:
            Intent intentFile = new Intent(getActivity(), FileLoadEvaluationActivity.class);
            startActivity(intentFile);
            break;
}
    }
}
