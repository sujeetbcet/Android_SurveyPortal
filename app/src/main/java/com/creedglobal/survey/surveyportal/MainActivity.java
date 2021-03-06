package com.creedglobal.survey.surveyportal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;


import com.creedglobal.survey.surveyportal.Database.DBHandler;
import com.creedglobal.survey.surveyportal.Info.Constraints;
import com.creedglobal.survey.surveyportal.Info.Data;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    DBHandler db;
    Profile profile;
    EditText edt_usr, edt_pwd;
    String username, password;
    boolean loginStatus = false;
    int id = 1;
    ViewFlipper flipper;
    int res[] = {
            R.drawable.me,
            R.drawable.me1,
            R.drawable.me3,
            R.drawable.me2
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(this);
        FacebookSdk.isInitialized();
        setContentView(R.layout.activity_main);
        edt_usr = (EditText) findViewById(R.id.edt_username);
        edt_pwd = (EditText) findViewById(R.id.edt_password);
//        loginButton = (LoginButton) findViewById(R.id.login_button);
        flipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        if (loginStatus = false) {
            startActivity(new Intent(getApplicationContext(), MainScreen.class));
        } else {

            int width = getWindow().getWindowManager().getDefaultDisplay().getWidth();
            // adding all images in flipper
            for (int i = 0; i < res.length; i++) {
                ImageView image = new ImageView(this);
                image.setImageResource(res[i]);
                flipper.addView(image);
            }
            //set in/out flipping animation
            flipper.setInAnimation(this, android.R.anim.fade_in);
//            flipper.setOutAnimation(this, android.R.anim.fade_out);
            flipper.setAutoStart(true);
            flipper.setFlipInterval(Constraints.flipperInterval);
        }
    }


    public void gotoRegister(View view) {
        startActivity(new Intent(getApplicationContext(), Registration.class));
        finish();
    }

    public void forgotPassword(View view) {
        startActivity(new Intent(getApplicationContext(), Forgot.class));
    }

    public void clickLogin(View view) {
        username = edt_usr.getText().toString();
        password = edt_pwd.getText().toString();
        if (db.checkLogin(username, password)) {
            Data.username = username;
            Data.email = username;
            Log.i("infoo", "Login Success");
            startActivity(new Intent(getApplicationContext(), MainScreen.class));
            finish();

        } else {
            Toast.makeText(this, "Wrong username/password", Toast.LENGTH_LONG).show();
            Log.i("infoo", "Login Failed");
        }
    }

}
