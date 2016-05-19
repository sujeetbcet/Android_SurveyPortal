package com.creedglobal.survey.surveyportal;

import android.app.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;

import com.creedglobal.survey.surveyportal.Info.Constraints;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.NameValuePair;


public class SplashScreen extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        FacebookSdk.sdkInitialize(getApplicationContext());
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */


            @Override
            public void run() {

                if (AccessToken.getCurrentAccessToken() != null) {
                    Profile profile = Profile.getCurrentProfile();
                    Log.i("my_info", "user name" + profile.getName());

                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(getApplicationContext(), MainScreen.class);
                    startActivity(i);

                } else {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
                finish();
            }
        }, Constraints.splashScreenTimeOut);


        JSONObject jsonObject = null;
        JSONArray employees = null;
        try {
            jsonObject = new JSONObject(Constraints.jsonEmployee);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            employees = jsonObject.getJSONArray("employees");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String firstname = null,lastname = null;
        ArrayList<HashMap<String,String>> nameValuePair = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> contact = new HashMap<String, String>();

        for (int i=0;i<employees.length();i++){
            try {
                jsonObject = employees.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                firstname = jsonObject.getString("firstName");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                lastname  = jsonObject.getString("lastName");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            contact.put("TAG_F",firstname);
            contact.put("TAG_L",lastname);
            nameValuePair.add(contact);
        }
        for (int i=0;i<2;i++)
        Log.i("infoo",nameValuePair.get(i).get("TAG_F"));

    }
}
