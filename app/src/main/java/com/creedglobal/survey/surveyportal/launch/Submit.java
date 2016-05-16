package com.creedglobal.survey.surveyportal.launch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.creedglobal.survey.surveyportal.Database.DBHandler;
import com.creedglobal.survey.surveyportal.Info.Result;
import com.creedglobal.survey.surveyportal.R;
import com.creedglobal.survey.surveyportal.fragments.Home;

import java.util.Arrays;

//


public class Submit extends AppCompatActivity {
    EditText edt_name,edt_email, edt_mobile, edt_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        edt_name = (EditText) findViewById(R.id.edt_customer_name);
        edt_email=(EditText) findViewById(R.id.edt_customer_email);
        edt_mobile =(EditText) findViewById(R.id.edt_customer_mobile);
        edt_msg =(EditText) findViewById(R.id.edt_customer_msg);
    }

    public void submit(View view){
        if (validate()){
            Result.selectedOption[11]= edt_name.getText().toString();
            Result.selectedOption[12]=edt_email.getText().toString();
            Result.selectedOption[13]= edt_mobile.getText().toString();
            Result.selectedOption[14]= edt_msg.getText().toString();
            Log.i("infoo","value in array is :"+Arrays.deepToString(Result.selectedOption));
            DBHandler db=new DBHandler(this);
            if (db.submitResult(Result.selectedOption))
            {
                Toast.makeText(getApplicationContext(),"Your result is submitted Successfully",Toast.LENGTH_SHORT).show();
                db.close();
                startActivity(new Intent(getApplicationContext(),SurveyLauncher.class));
            }
            else {
                Toast.makeText(getApplicationContext(),"ops...! something went wrong",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public boolean validate(){
        boolean valid=true;

        if (edt_name.getText().toString().isEmpty()) {
            edt_name.setError("you can't leave this empty");
            valid = false;
        }
        if (edt_email.getText().toString().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(edt_email.getText().toString()).matches()) {
            edt_email.setError("enter a valid email address");
            valid = false;
        }
        if (!Patterns.PHONE.matcher(edt_mobile.getText().toString()).matches() || edt_mobile.getText().toString().length() < 10) {
            edt_mobile.setError("Enter Valid Mobile Number");
            valid = false;
        }
        return valid;
    }

    // AsyncTask for sending mail to customer
    
}