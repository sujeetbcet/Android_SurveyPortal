package com.creedglobal.survey.surveyportal.launch;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creedglobal.survey.surveyportal.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SurveyScreenActivityFragment extends Fragment {

    public SurveyScreenActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_survey_screen, container, false);
    }
}
