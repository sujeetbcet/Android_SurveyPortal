package com.creedglobal.survey.surveyportal.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.creedglobal.survey.surveyportal.MainScreen;
import com.creedglobal.survey.surveyportal.R;

import java.util.regex.Pattern;

public class Feedback extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feedback, container, false);
        ((MainScreen) getActivity()).getSupportActionBar().setTitle("Feedback");

        // Inflate the layout for this fragment
        return rootView;
    }


}
