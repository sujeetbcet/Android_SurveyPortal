package com.creedglobal.survey.surveyportal;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.creedglobal.survey.surveyportal.Create.CreateSurvey;
import com.creedglobal.survey.surveyportal.Database.DBHandler;
import com.creedglobal.survey.surveyportal.Info.Constraints;
import com.creedglobal.survey.surveyportal.fragments.About;
import com.creedglobal.survey.surveyportal.fragments.Home;
import com.creedglobal.survey.surveyportal.fragments.ResultAllSurvey;
import com.creedglobal.survey.surveyportal.fragments.Setting;
import com.creedglobal.survey.surveyportal.fragments.Support;
import com.creedglobal.survey.surveyportal.fragments.Sync;
import com.creedglobal.survey.surveyportal.launch.SurveyLauncher;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager manager;
    FragmentTransaction transaction;
    DBHandler controller = new DBHandler(this);
    ProgressDialog prgDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prgDialog = new ProgressDialog(this);
        // Handle the Home action
        Home frag = new Home();
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentcontainer, frag, "home_fragment");
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Do you want to exit ?");
            builder.setCancelable(false);
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    finish();

                    System.exit(1);
                }
            });
            builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            manager = getFragmentManager();
            transaction = manager.beginTransaction();
            transaction.replace(R.id.fragmentcontainer, new Setting());
            transaction.commit();
            return true;
        }
        if (id == R.id.action_logout) {
            SharedPreferences pref = MainScreen.this.getSharedPreferences("myPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            Toast.makeText(MainScreen.this, "Logout", Toast.LENGTH_SHORT).show();
            editor.clear();
            editor.commit();
            MainScreen.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
//            manager.popBackStackImmediate(null,manager.POP_BACK_STACK_INCLUSIVE);
            manager = getFragmentManager();
            transaction = manager.beginTransaction();
            transaction.replace(R.id.fragmentcontainer, new Home());
            transaction.commit();

        } else if (id == R.id.nav_survey) {
            startActivity(new Intent(getApplicationContext(), SurveyLauncher.class));

        } else if (id == R.id.nav_results) {
//            manager.popBackStackImmediate(null,manager.POP_BACK_STACK_INCLUSIVE);
            manager = getFragmentManager();
            transaction = manager.beginTransaction();
            transaction.replace(R.id.fragmentcontainer, new ResultAllSurvey());
            transaction.commit();


        } else if (id == R.id.nav_sync) {

//            manager.popBackStackImmediate(null,manager.POP_BACK_STACK_INCLUSIVE);
            syncSQLiteToRemote();
//            manager = getFragmentManager();
//            transaction = manager.beginTransaction();
//            transaction.replace(R.id.fragmentcontainer,new Sync());
//            transaction.commit();

        } else if (id == R.id.nav_about) {
//            manager.popBackStackImmediate(null,manager.POP_BACK_STACK_INCLUSIVE);
            manager = getFragmentManager();
            transaction = manager.beginTransaction();
            transaction.replace(R.id.fragmentcontainer, new About());
            transaction.commit();

        } else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "http://creedglobal.com/";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "SurveyPortal");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        } else if (id == R.id.nav_rate) {
            String url = "http://creedglobal.com/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } else if (id == R.id.nav_feed) {
//            manager = getFragmentManager();
//            transaction = manager.beginTransaction();
//            transaction.replace(R.id.fragmentcontainer,new Feedback());
//            transaction.commit();

        } else if (id == R.id.nav_help) {
            manager = getFragmentManager();
            transaction = manager.beginTransaction();
            transaction.replace(R.id.fragmentcontainer, new Support());
            transaction.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void gotoCreateSurvey(View view) {
        startActivity(new Intent(this, CreateSurvey.class));
    }

    public void syncSQLiteToRemote() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        ArrayList<HashMap<String, String>> userList = controller.getAllUsers();
        System.out.println("userlist > "+userList);
        if (userList.size() != 0) {
            if (controller.dbSyncCount() != 0) {
                prgDialog.show();
                System.out.println("json request > "+controller.composeJSONfromSQLite());
                params.put("syncData", controller.composeJSONfromSQLite());
                System.out.println("actual request > "+params);
                client.post(Constraints.syncURL, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        System.out.println("response from server"+response);
                        prgDialog.hide();
                        try {
                            JSONArray arr = new JSONArray(response);
                            System.out.println("resonse size  "+arr.length());
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject obj = (JSONObject) arr.get(i);
                                System.out.println("survey "+obj.get("survey"));
                                System.out.println("customer_email "+obj.get("customer_email"));
                                System.out.println("admin_email "+obj.get("admin_email"));
                                System.out.println("syncStatus "+obj.get("syncStatus"));
                                controller.updateSyncStatus(obj.get("customer_email").toString(),obj.get("survey").toString(),obj.get("syncStatus").toString());
                            }
                            Toast.makeText(getApplicationContext(), "DB Sync completed!", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {
                        // TODO Auto-generated method stub
                        prgDialog.hide();
                        if (statusCode == 404) {
                            Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                        } else if (statusCode == 500) {
                            Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                        }
                    }

                });
            } else {
                Toast.makeText(getApplicationContext(), "Your Application and Server are in Sync!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "No data to perform Sync", Toast.LENGTH_LONG).show();
        }
    }


}
