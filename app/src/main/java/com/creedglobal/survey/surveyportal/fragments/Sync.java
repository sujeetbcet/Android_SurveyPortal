package com.creedglobal.survey.surveyportal.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.creedglobal.survey.surveyportal.R;

import java.util.regex.Pattern;

public class Sync extends Fragment {
    private static final String phoneRegex = "08088484807";//you can just place your support phone here
    private static final Pattern phoneMatcher = Pattern.compile(phoneRegex);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_support,container,false);
        ImageView twitter =(ImageView)rootView.findViewById(R.id.twitter);
        ImageView linkedin =(ImageView)rootView.findViewById(R.id.linkedin);
        ImageView facebook =(ImageView)rootView.findViewById(R.id.facebook);
        TextView Corporate_office = (TextView)rootView.findViewById(R.id.txt_corporate);
        final TextView Contact = (TextView)rootView.findViewById(R.id.txt_contact);
//        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Contact");
        SpannableString spanStr = new SpannableString(getString(R.string.head_add));
        spanStr.setSpan(new UnderlineSpan(), 0, spanStr.length(), 0);
        Corporate_office.setText(spanStr);
        Corporate_office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent geoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="
                        +"Creed Technologies Pvt. Ltd.Level 9th, Delta Block,Sigma Tech-Park,Whitefield Main Road, Whitefield Bangalore 560 066"));
                startActivity(geoIntent);
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String url = "https://twitter.com/creedglobal";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });
        linkedin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String url = "https://www.linkedin.com/company/creed-global-technologies";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String url = "https://www.facebook.com/creedglobal/?fref=ts";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        linkify(Contact);

        // Inflate the layout for this fragment
        return rootView;
    }


    public static void linkify(TextView text) {
        Linkify.addLinks(text, phoneMatcher, "tel:");
    }

}
