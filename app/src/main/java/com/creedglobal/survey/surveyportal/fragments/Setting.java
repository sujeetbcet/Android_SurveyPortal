package com.creedglobal.survey.surveyportal.fragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.creedglobal.survey.surveyportal.Database.DBHandler;
import com.creedglobal.survey.surveyportal.Database.UserAdmin;
import com.creedglobal.survey.surveyportal.Info.Data;
import com.creedglobal.survey.surveyportal.MainActivity;
import com.creedglobal.survey.surveyportal.MainScreen;
import com.creedglobal.survey.surveyportal.R;

/**
 * Created by SUJEET on 5/11/2016.
 */
public class Setting extends Fragment {
    UserAdmin userAdmin=new UserAdmin();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBHandler dbHandler = new DBHandler(getActivity());
        if (Data.email.isEmpty()){
            Toast.makeText(getActivity(),"please login",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
        else {
            userAdmin=dbHandler.getUserAdminDetail(Data.email);
        }
        if (dbHandler!=null)
            dbHandler.close();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings,container,false);
        ((MainScreen) getActivity()).getSupportActionBar().setTitle("Settings");
        final TextView name = (TextView) rootView.findViewById(R.id.txt_fullname);
        final TextView email = (TextView) rootView.findViewById(R.id.txt_email);
        final EditText mobile = (EditText) rootView.findViewById(R.id.edt_Phone);
        final EditText occupation=(EditText) rootView.findViewById(R.id.edt_occupation);
        final EditText address= (EditText) rootView.findViewById(R.id.edt_address);
        name.setText(userAdmin.getName());
        email.setText(userAdmin.getEmail());
        mobile.setText(userAdmin.getMobile());
        occupation.setText(userAdmin.getOccupation());
        address.setText(userAdmin.getAddress());

        Button btn_submit= (Button) rootView.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler db = new DBHandler(getActivity());

                userAdmin.setName(name.getText().toString());
                userAdmin.setMobile(mobile.getText().toString());
                userAdmin.setEmail(email.getText().toString());
                userAdmin.setOccupation(occupation.getText().toString());
                userAdmin.setAddress(address.getText().toString());

                if (db.UpdateUserAdminDetail(userAdmin))
                    Toast.makeText(getActivity(),"profile updated...",Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(getActivity(),"Ops...please try again",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }
}
