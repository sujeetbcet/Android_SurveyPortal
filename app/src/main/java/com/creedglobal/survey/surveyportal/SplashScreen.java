package com.creedglobal.survey.surveyportal;

import android.app.Activity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.creedglobal.survey.surveyportal.Info.Constraints;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.Profile;


public class SplashScreen extends Activity {
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        //StartAnimations();
       StartAnimations1();
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timerThread.start();
    }

//    private void StartAnimations() {
//
//        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
//        anim.reset();
//        ImageView iv = (ImageView) findViewById(R.id.imageView4);
//        iv.clearAnimation();
//        iv.startAnimation(anim);
//
//    }

    private void StartAnimations1() {
        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim1.setStartOffset(700);
        anim1.reset();
        ImageView v = (ImageView) findViewById(R.id.imageView4);
        v.clearAnimation();
        v.startAnimation(anim1);

    }
}

