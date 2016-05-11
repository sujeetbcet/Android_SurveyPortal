package com.creedglobal.survey.surveyportal.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.creedglobal.survey.surveyportal.Database.DBHandler;
import com.creedglobal.survey.surveyportal.R;
import com.creedglobal.survey.surveyportal.extra.DisplaySurveyResponse;
import com.creedglobal.survey.surveyportal.launch.Question1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by SUJEET on 5/10/2016.
 */
public class ResultAllSurvey extends Fragment {
    View rootView;
    DBHandler db;
    Cursor cursor;
    ListView lv;
    String selectedSurvey;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("infoo","Fragment_onCreate is called");

        db=new DBHandler(getActivity());
        cursor=db.getAllSurvey();


    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("infoo","Fragment_onCreateView is alled...");
        TextView title= (TextView) container.findViewById(R.id.txt_survey_title);
        title.setText("Results...");
        rootView = inflater.inflate(R.layout.fragment_resultallsurvey,container,false);
        lv= (ListView) rootView.findViewById(R.id.list_result);
        if (cursor!=null){
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
                    R.layout.surveyitem,
                    cursor,
                    new String[]{"_id"},new int[]{R.id.txt_item},0);

            lv.setAdapter(adapter);
        }
        else {

            Log.i("infoo","No item in table");
        }
        // setting the click event on listview
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor= (Cursor) lv.getItemAtPosition(position);
                selectedSurvey = cursor.getString(0);
                db=new DBHandler(getActivity());
                Log.i("infoo","selected survey is : "+ selectedSurvey);
                Intent intent=new Intent(getActivity(),DisplaySurveyResponse.class);
                intent.putExtra("TAG_selectedSurvey",selectedSurvey);
                startActivity(intent);
            }
        });

        return rootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("infoo","Fragment_onDestroy is alled...");
        if (cursor!=null)
            cursor.close();
        if (db!=null)
            db.close();
    }

}
