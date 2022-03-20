package com.snowleopard.bamboo.fragment.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

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
    private ImageView iv_hint;

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
        iv_hint=getView().findViewById(R.id.iv_text_hint);
        textLoad.setOnClickListener(this);
        photoLoad.setOnClickListener(this);
        fileLoad.setOnClickListener(this);
        iv_hint.setOnClickListener(this);
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
        case R.id.iv_text_hint:
            View popHint = getActivity().getLayoutInflater().inflate(R.layout.dialog_pop_hint,null,false);
            PopupWindow popupWindow = new PopupWindow(popHint,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);

//            popupWindow.setAnimationStyle(R.style.pop_menu_animStyle);
//            popupWindow.showAtLocation(popHint, Gravity.TOP,0,0);
            popupWindow.setFocusable(true);
            //允许外部点击消失
            popupWindow.setOutsideTouchable(true);
            /**相对于某个控件的下方，并且在View这个布局中能找到对应的控件*/
            popupWindow.showAsDropDown(view.findViewById(R.id.iv_text_hint), 0,
                    0);

}
    }
}
