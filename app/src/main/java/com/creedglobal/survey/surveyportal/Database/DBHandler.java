package com.creedglobal.survey.surveyportal.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cp on 4/20/2016.
 */
public class DBHandler extends SQLiteOpenHelper {
    String queryString;

    // Database Version
    private static final int DATABASE_VERSION = 8;
    // Database Name
    public static final String DATABASE_NAME = "SurveyPortal.db";
    // Contacts table name
    public static final String TABLE_NAME_SURVEY = "Survey";         // to store data when user create a survey
    public static final String TABLE_NAME_USER = "Survey_user";     //  to store user details when signup/edit
    public static final String TABLE_NAME_CUSTOMER = "Survey_customer"; // to store customer detail after completed the survey. when click on SUBMIT button


    // Survey Table Columns names
    private static final String KEY_QID = "Qid";
    private static final String KEY_Surveyname = "surveyname";
    private static final String KEY_Question = "question";
    private static final String KEY_RESPONSE_1 = "option_1";
    private static final String KEY_RESPONSE_2 = "option_2";
    private static final String KEY_RESPONSE_3 = "option_3";
    private static final String KEY_RESPONSE_4 = "option_4";

    //Survey_customer table columns
    public static final String KEY_Username = "username";      // to Capture Full Name
    public static final String KEY_Mobile = "mobile";
    private static final String KEY_Email = "email";
    private static final String KEY_Msg = "message";
    private static final String KEY_Cust_Surveyname = "survey";
    private static final String KEY_Ques_1 = "question1";
    private static final String KEY_Ques_2 = "question2";
    private static final String KEY_Ques_3 = "question3";
    private static final String KEY_Ques_4 = "question4";
    private static final String KEY_Ques_5 = "question5";
    private static final String KEY_Ques_6 = "question6";
    private static final String KEY_Ques_7 = "question7";
    private static final String KEY_Ques_8 = "question8";
    private static final String KEY_Ques_9 = "question9";
    private static final String KEY_Ques_10 = "question10";
    private static final String KEY_Temp_1 = "temp_1";

    //Survey_user table columns
    private static final String KEY_ADMIN_NAME = "admin_name";
    private static final String KEY_ADMIN_Mobile_nos = "mobile";
    private static final String KEY_ADMIN_Email_id = "email";
    private static final String KEY_ADMIN_Occupation = "occupation";
    private static final String KEY_ADMIN_Password = "password";
    private static final String KEY_ADMIN_Temp_2 = "temp2";
    private static final String KEY_ADMIN_Temp_3 = "temp3";
    private static final String KEY_ADMIN_User_Type = "type";
    private static final String KEY_ADMIN_Token = "token";


    private static final String CREATE_TABLE_NAME_SURVEY = "CREATE TABLE " + TABLE_NAME_SURVEY + "("
            + KEY_QID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_Surveyname + " TEXT ,"
            + KEY_Question + " TEXT," + KEY_RESPONSE_1 + " TEXT," + KEY_RESPONSE_2 + " TEXT," + KEY_RESPONSE_3 + " TEXT,"
            + KEY_RESPONSE_4 + " TEXT" + ");";

    private static final String CREATE_TABLE_CUSTOMER = "CREATE TABLE " + TABLE_NAME_CUSTOMER
            + "("
            + KEY_Username + " TEXT,"
            + KEY_Mobile + " INTEGER,"
            + KEY_Email + " VARCHAR,"
            + KEY_Msg + " TEXT, "
            + KEY_Cust_Surveyname + " TEXT,"
            + KEY_Ques_1 + " TEXT, "
            + KEY_Ques_2 + " TEXT, "
            + KEY_Ques_3 + " TEXT, "
            + KEY_Ques_4 + " TEXT, "
            + KEY_Ques_5 + " TEXT, "
            + KEY_Ques_6 + " TEXT, "
            + KEY_Ques_7 + " TEXT, "
            + KEY_Ques_8 + " TEXT, "
            + KEY_Ques_9 + " TEXT, "
            + KEY_Ques_10 + " TEXT, "
            + KEY_Temp_1 + " TEXT "
            + ");";

    private static final String CREATE_TABLE_SURVEY_USER = "CREATE TABLE " + TABLE_NAME_USER
            + "("
            + KEY_ADMIN_NAME + " TEXT,"
            + KEY_ADMIN_Mobile_nos + " INTEGER,"
            + KEY_ADMIN_Email_id + " TEXT,"
            + KEY_ADMIN_Occupation + " TEXT, "
            + KEY_ADMIN_Password + " TEXT, "
            + KEY_ADMIN_Temp_2 + " TEXT, "
            + KEY_ADMIN_Temp_3 + " TEXT, "
            + KEY_ADMIN_User_Type + " TEXT DEFAULT 'guest', "
            + KEY_ADMIN_Token + " TEXT"
            + ");";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
//table create statements


    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(CREATE_TABLE_NAME_SURVEY);
        db.execSQL(CREATE_TABLE_SURVEY_USER);
        db.execSQL(CREATE_TABLE_CUSTOMER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SURVEY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CUSTOMER);

        // Create tables again
        onCreate(db);
    }
    //crud operation for survey

    //    to getAll survey available
    public Cursor getAllSurvey() {
        SQLiteDatabase db = this.getReadableDatabase();
//        "select rowid as _id,surveyname from  Survey";
        String query = "select distinct surveyname as _id from  Survey";
        Cursor c = db.rawQuery(query, null);
//        db.close(); // Closing database connection
        return c;

    }

    public Cursor getQdata(String surveyName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from "+TABLE_NAME_SURVEY+" where "+KEY_Surveyname+"=\"" + surveyName + "\";";
        Log.i("infoo", "query is :" + query);
        Cursor c = db.rawQuery(query, null);
        return c;
    }

    public int getTotalQuestion(String surveyName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int tq = 0;
        String query = "select * from " + TABLE_NAME_SURVEY + " where " + KEY_Surveyname + "=\"" + surveyName + "\"";
        Cursor cursor = db.rawQuery(query, null);
        tq = cursor.getCount();
        cursor.close();
        db.close();
        return tq;
    }

    // Adding new contact
    public boolean addDetails(Details_db details) {
        boolean status = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, details.getName()); // Contact Name
//        values.put(KEY_PHONE, details.getPhone()); // Contact Phone
        values.put(KEY_Surveyname, details.getSurveyname());
        values.put(KEY_Question, details.getQuestion());
        values.put(KEY_RESPONSE_1, details.getResponse_1());
        values.put(KEY_RESPONSE_2, details.getResponse_2());
        values.put(KEY_RESPONSE_3, details.getResponse_3());
        values.put(KEY_RESPONSE_4, details.getResponse_4());
        // Inserting Row
        db.insert(TABLE_NAME_SURVEY, null, values);
        db.close(); // Closing database connection
        Log.i("infoo : ", "addDetails>DBHandler . details inserted & connection closed ");

        if (db != null) {
            db.close();
        }

        status = true;
        return status;
    }
//    Inserting the data in customer table after completing the survey.
    public boolean submitResult(String[] args){
        boolean status=false;
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_Cust_Surveyname,args[0]);
        values.put(KEY_Ques_1,args[1]);
        values.put(KEY_Ques_2,args[2]);
        values.put(KEY_Ques_3,args[3]);
        values.put(KEY_Ques_4,args[4]);
        values.put(KEY_Ques_5,args[5]);
        values.put(KEY_Ques_6,args[6]);
        values.put(KEY_Ques_7,args[7]);
        values.put(KEY_Ques_8,args[8]);
        values.put(KEY_Ques_9,args[9]);
        values.put(KEY_Ques_10,args[10]);
        values.put(KEY_Username,args[11]);
        values.put(KEY_Email,args[12]);
        values.put(KEY_Mobile,args[13]);
        values.put(KEY_Msg,args[14]);

        db.insert(TABLE_NAME_CUSTOMER,null,values);
        db.close();
        status=true;
        Log.i("infoo"," Survey result inserted in DB, with result status "+status);
        return status;
    }
//    registration . inserting into survry_user table
    public boolean register(String[] userdetails){
        boolean status=false;
        SQLiteDatabase db=this.getReadableDatabase();
        queryString="select * from "+TABLE_NAME_USER+" where "+KEY_Email+"=\""+userdetails[1]+"\"";

        if ((db.rawQuery(queryString,null)).moveToFirst()){
            status=false;
            Log.i("infoo","user already registered with this email id "+userdetails[1]+" status "+status);
            db.close();
            return status;
        }
        else {
//            db.close();
            db=this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_ADMIN_NAME,userdetails[0]);
            values.put(KEY_ADMIN_Email_id,userdetails[1]);
            values.put(KEY_ADMIN_Password,userdetails[2]);
            values.put(KEY_ADMIN_Mobile_nos,userdetails[3]);
            values.put(KEY_ADMIN_Occupation,userdetails[4]);
            db.insert(TABLE_NAME_USER,null,values);
            status=true;
            Log.i("info","Successfully registered with email id "+userdetails[1]+" status "+status);
        }
        db.close();
        return status;
    }

    // Getting single contact
    public  Details_db getDetail(int qid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_SURVEY, new String[] {  KEY_Surveyname,KEY_Question,KEY_RESPONSE_1,KEY_RESPONSE_2,KEY_RESPONSE_3,KEY_RESPONSE_4, }, KEY_QID + "=?", new String[] { String.valueOf(qid) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Details_db detail = new Details_db();
        // return contact
        return detail;
    }

    public Details_db getQuestion(String surveyname,int qid) {
        List<Details_db> detailsList = new ArrayList<Details_db>();
        Details_db details = new Details_db();
        SQLiteDatabase db = this.getReadableDatabase();


//        String smtp=" select * from survey where surveyname=\"creed"+"\" and rowid="+qid;
        String sqlquery=" select * from survey where surveyname=\""+surveyname+"\" and rowid="+qid;
//        Cursor cursor = db.query(TABLE_NAME_SURVEY, new String[] {  KEY_Surveyname,KEY_Question,KEY_RESPONSE_1,KEY_RESPONSE_2,KEY_RESPONSE_3,KEY_RESPONSE_4, }, KEY_QID + "=?", new String[] { String.valueOf(qid) }, null, null, null, null);
        Cursor cursor=db.rawQuery(sqlquery,null);
        if (cursor.moveToFirst()) {
            do {
//                Details_db details = new Details_db();
                details.setQid(cursor.getShort(0));
                details.setSurveyname(cursor.getString(1));
                details.setQuestion(cursor.getString(2));
                details.setResponse_1(cursor.getString(3));
                details.setResponse_2(cursor.getString(4));
                details.setResponse_3(cursor.getString(5));
                details.setResponse_4(cursor.getString(6));
                // Adding all details to list
                detailsList.add(details);
            }
            while (cursor.moveToNext());
            Log.i("my_info: ","getAllContacts>DBHandler. while-loop all data");
        }
        Log.i("my_info: ","getAllContacts>DBHandler. Fething & returning all data");

        cursor.close();
        db.close();
        // return details list
        return details;
    }

    // Getting All Contacts
    public List<Details_db> getAllContacts() {
        List<Details_db> detailsList = new ArrayList<Details_db>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_SURVEY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Details_db details = new Details_db();
//                details.setName(cursor.getString(0));
//                details.setPhone(cursor.getString(1));
                details.setSurveyname(cursor.getString(0));
                details.setQuestion(cursor.getString(1));
                details.setResponse_1(cursor.getString(2));
                details.setResponse_2(cursor.getString(3));
                details.setResponse_3(cursor.getString(4));
                details.setResponse_4(cursor.getString(5));
//                details.setComment(cursor.getString(7));

                // Adding contact to list
                detailsList.add(details);
            }
            while (cursor.moveToNext());
            Log.i("my_info: ","getAllContacts>DBHandler. while-loop all data");
        }
        Log.i("my_info: ","getAllContacts>DBHandler. Fething & returning all data");

        // return contact list
        return detailsList;
    }

    // Updating single contact
    public int updateContact(Details_db details) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, details.getName());
//        values.put(KEY_PHONE, details.getPhone());
        values.put(KEY_Surveyname,details.getSurveyname());
        values.put(KEY_Question,details.getQuestion());
        values.put(KEY_RESPONSE_1,details.getResponse_1());
        values.put(KEY_RESPONSE_2,details.getResponse_2());
        values.put(KEY_RESPONSE_3,details.getResponse_3());
        values.put(KEY_RESPONSE_4,details.getResponse_4());
//        values.put(KEY_COMMENT,details.getComment());


        // updating row
        return db.update(TABLE_NAME_SURVEY, values, KEY_Question + " = ?",
                new String[] { String.valueOf(details.getQuestion()) });
    }

    // Deleting single contact
    public void deleteContact(Details_db details) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_SURVEY, KEY_Question + " = ?",new String[] { String.valueOf(details.getQuestion()) });
        db.close();
    }



    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME_SURVEY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
// Crud operation for survey_user


    public void addDetails_user(Details_user details_user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, details.getName()); // Contact Name
//        values.put(KEY_PHONE, details.getPhone()); // Contact Phone
        values.put(KEY_Username,details_user.getUsername());
        values.put(KEY_Email,details_user.getEmail());
        values.put(KEY_Mobile,details_user.getMobile_no());
        values.put(KEY_Msg,details_user.getMsg());
        values.put(KEY_Ques_1,details_user.getQues_1());
        values.put(KEY_Ques_2,details_user.getQues_2());
        values.put(KEY_Ques_3,details_user.getQues_3());
        values.put(KEY_Ques_4,details_user.getQues_4());
        values.put(KEY_Ques_5,details_user.getQues_5());
        values.put(KEY_Temp_1,details_user.getTemp_1());


        // Inserting Row
        db.insert(TABLE_NAME_USER, null, values);
        db.close(); // Closing database connection
        Log.i("my_info : ","addDetails>DBHandler . details inserted & connection closed ");
    }

    // Getting single contact
    Details_user getDetails_user(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_USER, new String[] {  KEY_Username,KEY_Mobile,KEY_Email,KEY_Msg,KEY_Ques_1,KEY_Ques_2,KEY_Ques_3,KEY_Ques_4,KEY_Ques_5,KEY_Temp_1 }, KEY_Email + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Details_user details_user = new Details_user();
        // return contact
        return details_user;
    }

    // Getting All Contacts
    public List<Details_user> getAllContacts_user() {
        List<Details_user> detailsList_user = new ArrayList<Details_user>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor_user = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor_user.moveToFirst()) {
            do {
                Details_user details_user = new Details_user();
//                details.setName(cursor.getString(0));
//                details.setPhone(cursor.getString(1));
                details_user.setUsername(cursor_user.getString(0));
                details_user.setMobile_no(cursor_user.getString(1));
                details_user.setEmail(cursor_user.getString(2));
                details_user.setMsg(cursor_user.getString(3));
                details_user.setQues_1(cursor_user.getString(4));
                details_user.setQues_2(cursor_user.getString(5));
                details_user.setQues_3(cursor_user.getString(6));
                details_user.setQues_5(cursor_user.getString(7));
                details_user.setQues_4(cursor_user.getString(8));
                details_user.setTemp_1(cursor_user.getString(9));
//                details.setComment(cursor.getString(7));

                // Adding contact to list
                detailsList_user.add(details_user);
            }
            while (cursor_user.moveToNext());
            Log.i("my_info: ","getAllContacts>DBHandler. while-loop all data");
        }
        Log.i("my_info: ","getAllContacts>DBHandler. Fething & returning all data");

        // return contact list
        return detailsList_user;
    }

    // Updating single contact
    public int updateContact_user(Details_user details_user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, details.getName());
//        values.put(KEY_PHONE, details.getPhone());
        values.put(KEY_Username,details_user.getUsername());
        values.put(KEY_Email,details_user.getEmail());
        values.put(KEY_Mobile,details_user.getMobile_no());
        values.put(KEY_Msg,details_user.getMsg());
        values.put(KEY_Ques_1,details_user.getQues_1());
        values.put(KEY_Ques_2,details_user.getQues_2());
        values.put(KEY_Ques_3,details_user.getQues_3());
        values.put(KEY_Ques_4,details_user.getQues_4());
        values.put(KEY_Ques_5,details_user.getQues_5());
        values.put(KEY_Temp_1,details_user.getTemp_1());
//        values.put(KEY_COMMENT,details.getComment());


        // updating row
        return db.update(TABLE_NAME_USER, values, KEY_Email + " = ?",
                new String[] { String.valueOf(details_user.getEmail()) });
    }

    // Deleting single contact
    public void deleteContact_user(Details_user details_user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_USER, KEY_Email + " = ?",new String[] { String.valueOf(details_user.getEmail()) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount_user() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor_user = db.rawQuery(countQuery, null);
        cursor_user.close();

        // return count
        return cursor_user.getCount();
    }
    //  / Crud operation for survey_customer


    public void addDetails_customer(Customer_details_db details_customer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, details.getName()); // Contact Name
//        values.put(KEY_PHONE, details.getPhone()); // Contact Phone
        values.put(KEY_ADMIN_NAME,details_customer.getAdminname());
        values.put(KEY_ADMIN_Email_id,details_customer.getEmail_id());
        values.put(KEY_ADMIN_Mobile_nos,details_customer.getMobile_nos());
        values.put(KEY_ADMIN_Occupation,details_customer.getOccupation());
        values.put(KEY_ADMIN_Password,details_customer.getPswd());
        values.put(KEY_ADMIN_Temp_2,details_customer.getTemp_2());
        values.put(KEY_ADMIN_Temp_3,details_customer.getTemp_3());
        values.put(KEY_ADMIN_User_Type,details_customer.getUser_type());



        // Inserting Row
        db.insert(TABLE_NAME_CUSTOMER, null, values);
        db.close(); // Closing database connection
        Log.i("my_info : ","addDetails>DBHandler . details inserted & connection closed ");
    }

    // Getting single contact
    Customer_details_db getDetails_customer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME_CUSTOMER, new String[] {KEY_ADMIN_NAME, KEY_ADMIN_Mobile_nos, KEY_ADMIN_Email_id, KEY_ADMIN_Occupation, KEY_ADMIN_Password, KEY_ADMIN_Temp_2, KEY_ADMIN_Temp_3, KEY_ADMIN_User_Type}, KEY_Email + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Customer_details_db details_customer = new Customer_details_db();
        // return contact
        return details_customer;
    }

    // Getting All Contacts
    public List<Customer_details_db> getAllContacts_customer() {
        List<Customer_details_db> detailsList_Customer = new ArrayList<Customer_details_db>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_CUSTOMER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor_customer = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor_customer.moveToFirst()) {
            do {
                Customer_details_db details_customer = new Customer_details_db();
//                details.setName(cursor.getString(0));
//                details.setPhone(cursor.getString(1));
                details_customer.setAdminname(cursor_customer.getString(0));
                details_customer.setMobile_nos(cursor_customer.getString(1));
                details_customer.setEmail_id(cursor_customer.getString(2));
                details_customer.setOccupation(cursor_customer.getString(3));
                details_customer.setPswd(cursor_customer.getString(4));
                details_customer.setTemp_2(cursor_customer.getString(5));
                details_customer.setTemp_3(cursor_customer.getString(6));
                details_customer.setUser_type(cursor_customer.getString(7));
//                details.setComment(cursor.getString(7));
                // Adding contact to list
                detailsList_Customer.add(details_customer);
            }
            while (cursor_customer.moveToNext());
            Log.i("my_info: ","getAllContacts>DBHandler. while-loop all data");
        }
        Log.i("my_info: ","getAllContacts>DBHandler. Fething & returning all data");
        // return contact list
        return detailsList_Customer;
    }



    // Updating single contact
    public int updateContact_customer(Customer_details_db details_customer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, details.getName());
//        values.put(KEY_PHONE, details.getPhone());
        values.put(KEY_ADMIN_NAME,details_customer.getAdminname());
        values.put(KEY_Email,details_customer.getEmail_id());
        values.put(KEY_ADMIN_Mobile_nos,details_customer.getMobile_nos());
        values.put(KEY_ADMIN_Occupation,details_customer.getOccupation());
        values.put(KEY_ADMIN_Password,details_customer.getPswd());
        values.put(KEY_ADMIN_Temp_2,details_customer.getTemp_2());
        values.put(KEY_ADMIN_Temp_3,details_customer.getTemp_3());
        values.put(KEY_ADMIN_User_Type,details_customer.getUser_type());


//        values.put(KEY_COMMENT,details.getComment());


        // updating row
        return db.update(TABLE_NAME_CUSTOMER, values, KEY_Email + " = ?",
                new String[] { String.valueOf(details_customer.getEmail_id()) });
    }

    // Deleting single contact
    public void deleteContact_customer(Customer_details_db details_customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_CUSTOMER, KEY_Email + " = ?",new String[] { String.valueOf(details_customer.getEmail_id()) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount_customer() {
        queryString = "SELECT  * FROM " + TABLE_NAME_CUSTOMER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor_customer = db.rawQuery(queryString, null);
        cursor_customer.close();

        // return count
        return cursor_customer.getCount();
    }
    public boolean checkLogin(String usr,String pwd){
        boolean status=false;
        queryString="select * from "+TABLE_NAME_USER+" where "+KEY_ADMIN_Email_id+"=\""+usr+"\" and "+ KEY_ADMIN_Password +"=\""+pwd+"\"";
        SQLiteDatabase db=this.getReadableDatabase();
        if ((db.rawQuery(queryString,null)).moveToFirst())
         status = true;

        db.close();
        return status;
    }
    public List<SurveyResponse> getSurveyResults(String surveyName){
        List<SurveyResponse> responseDataList = new ArrayList<SurveyResponse>();
        queryString="select * from "+TABLE_NAME_CUSTOMER+" where "+KEY_Cust_Surveyname+"=\""+surveyName+"\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SurveyResponse response = new SurveyResponse();
                response.setUsername(cursor.getString(0));
                response.setMobile(cursor.getString(1));
                response.setEmail(cursor.getString(2));
                response.setComments(cursor.getString(3));
                response.setSurveyname(cursor.getString(4));
                response.setResponse1(cursor.getString(5));
                response.setResponse2(cursor.getString(6));
                response.setResponse3(cursor.getString(7));
                response.setResponse4(cursor.getString(8));
                response.setResponse5(cursor.getString(9));
                response.setResponse6(cursor.getString(10));
                response.setResponse7(cursor.getString(11));
                response.setResponse8(cursor.getString(12));
                response.setResponse9(cursor.getString(13));
                response.setResponse10(cursor.getString(14));
                // Adding contact to list
                responseDataList.add(response);
                Log.i("infoo",""+responseDataList.get(0).toString());
            }
            while (cursor.moveToNext());
            Log.i("my_info: ","getSurveyResults()>DBHandler. do-while-loop all data");
        }
        return responseDataList;
    }

    public Cursor getResult(String surveyname){
        queryString="select * from "+TABLE_NAME_CUSTOMER+" where "+KEY_Cust_Surveyname+"=\""+surveyname+"\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        return cursor;
    }
    // call this method to get admin profile details
    public UserAdmin getUserAdminDetail(String email){
        UserAdmin detail = new UserAdmin();
        queryString="select * from "+TABLE_NAME_USER+" where "+KEY_ADMIN_Email_id+"=\""+email+"\"";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if (cursor.moveToFirst()){
            detail.setName(cursor.getString(0));
            detail.setMobile(cursor.getString(1));
            detail.setEmail(cursor.getString(2));
            detail.setOccupation(cursor.getString(3));
            detail.setAddress(cursor.getString(5));
            detail.setUserType(cursor.getString(7));
        }
        if (cursor!=null)
            cursor.close();
        if (db!=null)
            db.close();
        return detail;
    }

    // call this mehod to update admin profile details
    public boolean UpdateUserAdminDetail(UserAdmin detail){

        boolean status=false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(KEY_ADMIN_NAME,detail.getName());
        value.put(KEY_ADMIN_Mobile_nos,detail.getMobile());
        value.put(KEY_ADMIN_Occupation,detail.getOccupation());
        value.put(KEY_ADMIN_Temp_2,detail.getAddress());
        if (db.update(TABLE_NAME_USER,value,KEY_ADMIN_Email_id+"=?", new String[]{detail.getEmail()})>0)
            status=true;
        closeResource(db,null);
        return status;
    }

    // it will close all open resource(if Opened), Call at the last of every method.
    public void closeResource(SQLiteDatabase db,Cursor cursor){
        if (cursor!=null)
            cursor.close();
        if (db!=null)
            db.close();
    }
//    public ArrayList<HashMap<String, String>> getAllUsers() {
//        ArrayList<HashMap<String, String>> wordList;
//        wordList = new ArrayList<HashMap<String, String>>();
//        String selectQuery = "SELECT  * FROM Survey_customer";
//        SQLiteDatabase database = this.getWritableDatabase();
//        Cursor cursor = database.rawQuery(selectQuery, null);
//        if (cursor.moveToFirst()) {
//            do {
//                HashMap<String, String> map = new HashMap<String, String>();
//                map.put("userId", cursor.getString(0));
//                map.put("username", cursor.getString(1));
//                map.put("mobile",cursor.getString(2));
//                map.put("email",cursor.getString(3));
//                map.put("message",cursor.getString(4));
//                map.put("survey",cursor.getString(5));
//                map.put("question1", cursor.getString(6));
//                map.put("question2",cursor.getString(7));
//                map.put("question3",cursor.getString(8));
//                map.put("question4",cursor.getString(9));
//                map.put("question5",cursor.getString(10));
//                map.put("question6", cursor.getString(11));
//                map.put("question7",cursor.getString(12));
//                map.put("question8",cursor.getString(13));
//                map.put("question8",cursor.getString(14));
//                map.put("question10",cursor.getString(15));
//                wordList.add(map);
//            } while (cursor.moveToNext());
//        }
//        database.close();
//        return wordList;
//    }
//
//    public String composeJSONfromSQLite(){
//        ArrayList<HashMap<String, String>> wordList;
//        wordList = new ArrayList<HashMap<String, String>>();
//        String selectQuery = "SELECT  * FROM Survey_customer where udpateStatus = '"+"no"+"'";
//        SQLiteDatabase database = this.getWritableDatabase();
//        Cursor cursor = database.rawQuery(selectQuery, null);
//        if (cursor.moveToFirst()) {
//            do {
//                HashMap<String, String> map = new HashMap<String, String>();
//                map.put("userId", cursor.getString(0));
//                map.put("username", cursor.getString(1));
//                map.put("mobile",cursor.getString(2));
//                map.put("email",cursor.getString(3));
//                map.put("message",cursor.getString(4));
//                map.put("survey",cursor.getString(5));
//                map.put("question1", cursor.getString(6));
//                map.put("question2",cursor.getString(7));
//                map.put("question3",cursor.getString(8));
//                map.put("question4",cursor.getString(9));
//                map.put("question5",cursor.getString(10));
//                map.put("question6", cursor.getString(11));
//                map.put("question7",cursor.getString(12));
//                map.put("question8",cursor.getString(13));
//                map.put("question8",cursor.getString(14));
//                map.put("question10",cursor.getString(15));
//                wordList.add(map);
//            } while (cursor.moveToNext());
//        }
//        database.close();
//        Gson gson = new GsonBuilder().create();
//        //Use GSON to serialize Array List to JSON
//        return gson.toJson(wordList);
//    }
//
//    /**
//     * Get Sync status of SQLite
//     * @return
//     */
//    public String getSyncStatus(){
//        String msg = null;
//        if(this.dbSyncCount() == 0){
//            msg = "SQLite and Remote MySQL DBs are in Sync!";
//        }else{
//            msg = "DB Sync needed";
//        }
//        return msg;
//    }
//
//    /**
//     * Get SQLite records that are yet to be Synced
//     * @return
//     */
//    public int dbSyncCount(){
//        int count = 0;
//        String selectQuery = "SELECT  * Survey_customer where udpateStatus = '"+"no"+"'";
//        SQLiteDatabase database = this.getWritableDatabase();
//         Cursor cursor = database.rawQuery(selectQuery, null);
//        count = cursor.getCount();
//        database.close();
//        return count;
//    }
//
//    /**
//     * Update Sync status against each User ID
//     * @param id
//     * @param status
//     */
//    public void updateSyncStatus(String id, String status){
//        SQLiteDatabase database = this.getWritableDatabase();
//        String updateQuery = "Update Survey_customer set udpateStatus = '"+ status +"' where userId="+"'"+ id +"'";
//        Log.d("query",updateQuery);
//        database.execSQL(updateQuery);
//        database.close();
//    }

}
