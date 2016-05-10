package com.creedglobal.survey.surveyportal.extra;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.creedglobal.survey.surveyportal.Database.DBHandler;
import com.creedglobal.survey.surveyportal.Database.SurveyResponse;
import com.creedglobal.survey.surveyportal.R;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

public class DisplaySurveyResponse extends AppCompatActivity {
    String selectedSurvey;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedSurvey = getIntent().getStringExtra("TAG_selectedSurvey");
        setContentView(R.layout.activity_display_survey_response);

        listView = (ListView) findViewById(R.id.list_showresult);
        listView.setAdapter(new ResultBaseAdapter(this));
        exportToCSV();

    }


    // create BaseAdapter, override all methods . And pass the object of this class to setAdapter

        private class ResultBaseAdapter extends BaseAdapter {
        Context context;
        public  List<SurveyResponse> responses = new ArrayList<SurveyResponse>();
        DBHandler db;

        ResultBaseAdapter(Context context){
            this.context=context;
            db = new DBHandler(context);
            responses=db.getSurveyResults(selectedSurvey);
        }

        @Override
        public int getCount() {
            Log.i("infoo","result size is :"+responses.size());
            return responses.size();
        }

        @Override
        public Object getItem(int position) {
            return responses.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.row_result,parent,false);

            TextView edt_name= (TextView) row.findViewById(R.id.txt_name);
            TextView edt_mobile= (TextView) row.findViewById(R.id.txt_mobile);
            TextView edt_email= (TextView) row.findViewById(R.id.txt_email);
            TextView edt_comment= (TextView) row.findViewById(R.id.txt_comment);
            TextView edt_response1= (TextView) row.findViewById(R.id.txt_response1);
            TextView edt_response2= (TextView) row.findViewById(R.id.txt_response2);
            TextView edt_response3= (TextView) row.findViewById(R.id.txt_response3);
            TextView edt_response4= (TextView) row.findViewById(R.id.txt_response4);
            TextView edt_response5= (TextView) row.findViewById(R.id.txt_response5);
            TextView edt_response6= (TextView) row.findViewById(R.id.txt_response6);
            TextView edt_response7= (TextView) row.findViewById(R.id.txt_response7);
            TextView edt_response8= (TextView) row.findViewById(R.id.txt_response8);
            TextView edt_response9= (TextView) row.findViewById(R.id.txt_response9);
            TextView edt_response10= (TextView) row.findViewById(R.id.txt_response10);





            SurveyResponse temp=responses.get(position);
//            parent.addView(edt_name);

            edt_name.setText(temp.getUsername());
            edt_mobile.setText(temp.getMobile());
            edt_email.setText(temp.getEmail());
            edt_comment.setText(temp.getComments());
            edt_response1.setText(temp.getResponse1());
            edt_response2.setText(temp.getResponse2());
            edt_response3.setText(temp.getResponse3());
            edt_response4.setText(temp.getResponse4());
            edt_response5.setText(temp.getResponse5());
            edt_response6.setText(temp.getResponse6());
            edt_response7.setText(temp.getResponse7());
            edt_response8.setText(temp.getResponse8());
            edt_response9.setText(temp.getResponse9());
            edt_response10.setText(temp.getResponse10());

            return row;
        }
    }
    public void exportToCSV(){
        File dbfile = getDatabasePath(DBHandler.DATABASE_NAME);
        DBHandler dbHandler = new DBHandler(this);
        File exportdir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportdir.exists()){
            exportdir.mkdirs();
        }
        File file = new File(exportdir,"result.csv");
        try
        {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = dbHandler.getReadableDatabase();
            Cursor cursor = dbHandler.getResult(selectedSurvey);
            csvWrite.writeNext(cursor.getColumnNames());
            while(cursor.moveToNext())
            {
                //Which column you want to exprort
                String arrStr[] ={cursor.getString(0),cursor.getString(1), cursor.getString(2)};
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            cursor.close();
        }
        catch(Exception sqlEx)
        {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }
    }
}
