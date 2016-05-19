package com.creedglobal.survey.surveyportal.Create;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.creedglobal.survey.surveyportal.Database.DBHandler;
import com.creedglobal.survey.surveyportal.Database.Details_db;
import com.creedglobal.survey.surveyportal.Info.Constraints;
import com.creedglobal.survey.surveyportal.MainScreen;
import com.creedglobal.survey.surveyportal.R;
import com.creedglobal.survey.surveyportal.launch.SurveyLauncher;

public class CreateSurvey extends AppCompatActivity {
    private ViewGroup mContainerView;
    int q = 0;
    EditText question_edit, opt1_edit, opt2_edit, opt3_edit, opt4_edit, surveyname_edit,vedit;
    TextView qid_text;
    String surveyName = null;
    int qid = 0, maxq = 5, minq = Constraints.minq, vintid = 1;
    String question, opt1, opt2, opt3, opt4, opt5;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_survey);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContainerView = (ViewGroup) findViewById(R.id.container);
        surveyname_edit = (EditText) findViewById(R.id.surveyname_edit);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.create_survey, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                NavUtils.navigateUpTo(this, new Intent(this, MainScreen.class));
                return true;

            case R.id.action_add_item:
                // Hide the "empty" view since there is now at least one item in the list.
                done(mContainerView);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void addQuestion(View view){
        if (qid < maxq) {
            addItem();
        } else {
            Snackbar.make(toolbar, "You cannot add more questions", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private void addItem() {

        if (surveyname_edit.getText().length() > 2) {
            if (hasTex(q)){
                // Instantiate a new "row" view.
                final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.list_item_addquestion, mContainerView, false);
                qid_text = (TextView) newView.findViewById(R.id.qidtxt);
                question_edit = (EditText) newView.findViewById(R.id.editText4);
                question_edit.requestFocus();
                opt1_edit = (EditText) newView.findViewById(R.id.editText5);
                opt2_edit = (EditText) newView.findViewById(R.id.editText6);
                opt3_edit = (EditText) newView.findViewById(R.id.editText7);
                opt4_edit = (EditText) newView.findViewById(R.id.editText8);

                // setting id to all view at run time and generating "Question no." i.e. "qid"
                qid_text.setText("Question " + ++qid + ".");

                question_edit.setId(++q);
                opt1_edit.setId(++q);
                opt2_edit.setId(++q);
                opt3_edit.setId(++q);
                opt4_edit.setId(++q);
                mContainerView.addView(newView, 0);

            }

        }
        else {
            surveyname_edit.requestFocus();
            surveyname_edit.setError("please enter valid survey name !");
        }

    }

    public void done(View view) {

        boolean isInsertedToDB=false;
        if (hasTex(q)){
//            view.setClickable(false);

            if (qid < minq) {
                Snackbar.make(view, "Please add atleast "+Constraints.minq+" questions", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).setActionTextColor(Color.RED).show();
//                Toast.makeText(this, "please add atleast 3 questions", Toast.LENGTH_LONG).show();
            } else {
                surveyName = surveyname_edit.getText().toString().toLowerCase();

                Log.i("my_infoo", "Done button is Clicked....");
                int eid = 0;

                for (int j = 0; j < qid; j++) {
//                Log.i("my_info","nested loop qid "+qid);
                    eid++;
                    EditText question_tmp = (EditText) findViewById(eid);
                    eid++;
                    EditText option1_tmp = (EditText) findViewById(eid);
                    eid++;
                    EditText option2_tmp = (EditText) findViewById(eid);
                    eid++;
                    EditText option3_tmp = (EditText) findViewById(eid);
                    eid++;
                    EditText option4_tmp = (EditText) findViewById(eid);

//                Capturing Question & options from UI . (1 by 1)
                    question = question_tmp.getText().toString();
                    opt1 = option1_tmp.getText().toString();
                    opt2 = option2_tmp.getText().toString();
                    opt3 = option3_tmp.getText().toString();
                    opt4 = option4_tmp.getText().toString();

                    DBHandler db = new DBHandler(this);
                    // Inserting Deatails
                    Log.d("Insert: ", "Inserting ..");
//                    boolean isInsertedToDB=false;
                    isInsertedToDB=db.addDetails(new Details_db(surveyName, question, opt1, opt2, opt3, opt4));

                    Toast.makeText(getApplicationContext(),"Survey created Successfully",Toast.LENGTH_SHORT).show();

                    Log.i("infoo","for loop isInserted"+isInsertedToDB+" qid "+qid+" loop-count "+j);
                }
                if (isInsertedToDB){
                    startActivity(new Intent(getApplicationContext(),SurveyLauncher.class));
                    finish();
                }
                else {

                    Snackbar.make(view, "survey not created...!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
//                    Toast.makeText(getApplicationContext(),"survey is not created successfully",Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

//    By calling this method it will validate all the editText field except Surveyname field,
//      return true, if all field has some Text
//      return false, if any field is empty and display proper error msg.

    public boolean hasTex(int editid) {
        Log.i("infoo","hasText starting");

        vintid =0;
        boolean status=true;

        for (int i=1;i<=editid;i++) {
            Log.i("infoo","hasText for-loop starting");
            vintid++;
            vedit= (EditText) findViewById(vintid);
            if (vedit.getText().length() >0) {
                Log.i("infoo","hasText for-loop>if .length()>1 starting");
                status = true;
//                vintid++;

            } else {

                Log.i("infoo","hasText for-loop>else starting");
                vedit.setError("error ");
                vedit.requestFocus();
                status = false;
//                vintid++;
                return status;
            }
        }
        Log.i("infoo","hasText for-loop ending with status "+status);
        return status;
    }
}