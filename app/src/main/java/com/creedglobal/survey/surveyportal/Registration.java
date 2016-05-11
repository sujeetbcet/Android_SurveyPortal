package com.creedglobal.survey.surveyportal;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.creedglobal.survey.surveyportal.Database.DBHandler;

public class Registration extends AppCompatActivity {

    EditText edt_username,edt_email,edt_password,edt_mobile,edt_occupation;
    String username,email,password,mobile,occupation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        edt_username = (EditText) findViewById(R.id.edt_name);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_mobile = (EditText) findViewById(R.id.edt_mobile);
        edt_occupation = (EditText) findViewById(R.id.edt_occupation);
    }
    public void loginPage(View view){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
    public void register(View view){
        if (validate()){

            String[] args={username,email,password,mobile,occupation};
            DBHandler db = new DBHandler(this);
            if (db.register(args)){
                Toast.makeText(this,"Successfully Registered",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MainScreen.class));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    this.finishAffinity();
                }
                else {
                    finish();
                }
            }
            else {
                edt_email.setError("Email Already Registered");
            }
        }
    }
    public boolean validate(){
        boolean valid=true;
        username=edt_username.getText().toString();
        email=edt_email.getText().toString();
        password=edt_password.getText().toString();
        mobile=edt_mobile.getText().toString();
        occupation=edt_occupation.getText().toString();

        if (username.isEmpty() || username.length() < 4) {
            edt_username.setError("at least 4 characters");
            valid = false;
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edt_email.setError("enter a valid email address");
            valid = false;
        }

        if (password.isEmpty() || password.length() < 6) {
            edt_password.setError("enter atleast 6 alphanumeric characters");
            valid = false;
        }

        if (mobile.isEmpty() || mobile.length() < 10 || mobile.length() > 10) {
            edt_mobile.setError("Enter Valid Mobile Number");
            valid = false;
        }
        if (occupation.isEmpty() || occupation.length() < 5) {
            edt_occupation.setError("at least 5 characters");
            valid = false;
        }
        return valid;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
//        super.onBackPressed();
    }
}
