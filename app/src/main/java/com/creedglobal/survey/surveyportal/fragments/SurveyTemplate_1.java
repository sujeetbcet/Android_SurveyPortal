package com.creedglobal.survey.surveyportal.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.creedglobal.survey.surveyportal.R;

/**
 * Created by SUJEET on 5/11/2016.
 */
public class SurveyTemplate_1 extends Fragment{
    View rootView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.survey_template_1,container,false);
        TextView textView = new TextView(getActivity());
        textView.setText("Question will display here...?");
        container.addView(textView);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
