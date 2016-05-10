package com.creedglobal.survey.surveyportal.launch;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.creedglobal.survey.surveyportal.R;
import com.creedglobal.survey.surveyportal.fragments.SurveyTemplate_1;

public class StartSurveyScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_survey_screen);
        Fragment fragment = new SurveyTemplate_1();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_container,fragment);
        transaction.commit();
    }
}
