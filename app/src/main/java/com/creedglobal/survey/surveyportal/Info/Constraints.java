package com.creedglobal.survey.surveyportal.Info;

/**
 * Created by SUJEET on 4/20/2016.
 */
public interface Constraints {
    public static final int maxq=5;
    public static final int minq=3;

    // This define the timeout to appear the next question after user select any option.
    public static final long delayTimeOut=500;
    public static final long splashScreenTimeOut=500;
    public static final int flipperInterval=2000;

    // credential for sending mail to customers
    public static final String sender_email="activity.getactivity@gmail.com";
    public static final String sender_password="creed@android";
    public static final String sender_subject="Survey Portal";
    public static final String sender_messageBody="Thanks for your valuable time...";

    public static final String jsonEmployee="{\"employees\":[{\"firstName\":\"John\", \"lastName\":\"Doe\"},{\"firstName\":\"Anna\", \"lastName\":\"Smith\"},{\"firstName\":\"Peter\", \"lastName\":\"Jones\"}]}";

    public static final String syncURL="http://zinee.in/demo/surveyportal/sync/response_sync.php";
    public static final String authenticateURL ="http://zinee.in/demo/surveyportal/sync/response_sync.php";
}
