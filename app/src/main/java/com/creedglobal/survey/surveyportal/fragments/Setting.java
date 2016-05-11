package com.creedglobal.survey.surveyportal.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creedglobal.survey.surveyportal.R;

/**
 * Created by SUJEET on 5/11/2016.
 */
public class Setting extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings,container,false);
        return rootView;
    }
}
