package com.digitalDreams.millionaire_game;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;

import androidx.annotation.Nullable;

class DBHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME="trivia.db";
    //SQLiteDatabase db;
    public static String JSON_TABLE = "json_table";
    public static String ID = "ID";
    public static String QUESTION = "QUESTION";
    public static final String ANSWER="ANSWER";
    public static final String TYPE = "TYPE";
    public static final String CORRECT = "CORRECT";
    public static final String LANGUAGE = "LANGUAGE";
    public static final String QUESTION_IMAGE="QUESTION_IMAGE";
    public static final String TITLE = "TITLE";
    public static final String LEVEL = "LEVEL";
    public static final String STAGE_NAME = "STAGE_NAME";
    public static final String STAGE = "STAGE";
    Context context;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 4);
        //db = getWritableDatabase();
        this.context =context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+ JSON_TABLE +" (ID TEXT PRIMARY KEY, QUESTION TEXT,ANSWER TEXT, TYPE TEXT,CORRECT TEXT,QUESTION_IMAGE TEXT, TITLE TEXT, LEVEL TEXT,LANGUAGE TEXT,STAGE_NAME TEXT,STAGE TEXT)");
        //createTable(MainActivity.columnList,sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+JSON_TABLE);
        sqLiteDatabase.execSQL("create table "+ JSON_TABLE +" (ID TEXT PRIMARY KEY, QUESTION TEXT,ANSWER TEXT, TYPE TEXT,CORRECT TEXT,QUESTION_IMAGE TEXT, TITLE TEXT, LEVEL TEXT,LANGUAGE TEXT,STAGE_NAME TEXT,STAGE TEXT)");


    }


    public void createTable(List<String> columnList,SQLiteDatabase db){
        String sqlText = "";
        for(int a=0;a<columnList.size();a++){
            String column = columnList.get(a);
            sqlText+=column+" TEXT, ";
        }
        sqlText = sqlText.substring(0,sqlText.length()-2);
        String CREATE_TABLE_SQL = "CREATE TABLE " + JSON_TABLE+ " ( sqlText )";
        db.execSQL(CREATE_TABLE_SQL);
    }

    public void insertDetails(List<String> column,List<String> data){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for(int a =0;a<data.size();a++) {
            for(int b =0;b<data.size();b++) {
                contentValues.put(column.get(b), data.get(a));
            }
        }
        long result = db.insert(JSON_TABLE,null,contentValues);

    }

    public void insertDetails(String language,String level,String id,
                              String content,String type,
                              String answer,String correct,
                              String stage_name, String stage){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LANGUAGE, language);
        contentValues.put(LEVEL,level);
        contentValues.put(ID,id);
        contentValues.put(QUESTION,content);
        contentValues.put(TYPE,type);
        contentValues.put(ANSWER,answer);
        contentValues.put(CORRECT,correct);
        contentValues.put(STAGE_NAME,stage_name);
        contentValues.put(STAGE,stage);
        long result = db.insert(JSON_TABLE,null,contentValues);

        Log.i("response","result "+result);
    }

    public Cursor getQuestionByLevel2(String level){
        SQLiteDatabase db = getWritableDatabase();
        if(!db.isOpen()){
            //db.close();
            db = getWritableDatabase();

        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        String game_level = sharedPreferences.getString("game_level","1");
        String current_play_level = sharedPreferences.getString("current_play_level","1");
        Log.i("current_play_level",current_play_level);


        String selectQuery = "SELECT * FROM " + JSON_TABLE + " where LEVEL = "+level+" ORDER BY RANDOM() LIMIT 1";
        //String selectQuery = "SELECT * FROM " + JSON_TABLE + " where LEVEL = "+level+" and STAGE = " +current_play_level+ " ORDER BY RANDOM() LIMIT 1";

        Log.i("99999999",selectQuery);
        Cursor res = db.rawQuery(selectQuery,null);
//        Cursor res1 = res;
//      while (res1.moveToNext()){
//          String id = res1.getString(res.getColumnIndex("ID"));
//          Log.i("99999999",id);
//
//      }
        if(res.getCount() < 1){
            String selectQuery2 = "SELECT * FROM " + JSON_TABLE + " where  STAGE = " +current_play_level+ " ORDER BY RANDOM() LIMIT 1";

            Cursor res2 = db.rawQuery(selectQuery2,null);
            db.close();
            return  res2;

        }
        db.close();


        return res;
    }

    public Cursor getLevels(){
        SQLiteDatabase db = this.getWritableDatabase();
        //String selectQuery = "SELECT * FROM " + JSON_TABLE + " where LEVEL = "+level+" ORDER BY RANDOM() LIMIT 1";
        String selectQuery = "SELECT * FROM " + JSON_TABLE +" ORDER BY  STAGE ASC";

        Cursor res = db.rawQuery(selectQuery,null);


        db.close();
        return res;


    }


    public Cursor getQuestionByLevel(String level){
       // Log.i("uuuuuuu",level);

        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        String game_level = sharedPreferences.getString("game_level","1");
        String current_play_level = sharedPreferences.getString("current_play_level","1");
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + JSON_TABLE + " where LEVEL = "+level;
        Cursor res = db.rawQuery(selectQuery,null);


        int count = res.getCount();
        int randomNumber ;
                if(count < 0) {
                    randomNumber =new Random().nextInt(30);
                }else {
                    randomNumber = new Random().nextInt(count);
                }
        String selectQuery1 = "SELECT * FROM " + JSON_TABLE + " where LEVEL = "+level+ " ORDER BY ID LIMIT "+randomNumber+",1";

        //String selectQuery1 = "SELECT * FROM " + JSON_TABLE + " where LEVEL = "+level+" ORDER BY ID LIMIT "+randomNumber+",1";
        Cursor res1 = db.rawQuery(selectQuery1,null);
        return res1;
    }

    public Cursor allQuestion(){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + JSON_TABLE;
        Cursor res = db.rawQuery(selectQuery,null);
        return res;
    }



    public int getQuestionSize(){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + JSON_TABLE;
        Cursor res = db.rawQuery(selectQuery,null);
        return res.getCount();
    }

    public String buildJson(){
        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject qObj = new JSONObject();
            JSONArray arr = new JSONArray();
            for(int a=1; a<16; a++) {
                Cursor res = getQuestionByLevel2(String.valueOf(a));

                res.moveToNext();
                @SuppressLint("Range") String id = res.getString(res.getColumnIndex("ID"));
                @SuppressLint("Range") String language = res.getString(res.getColumnIndex("LANGUAGE"));
                @SuppressLint("Range") String question = res.getString(res.getColumnIndex("QUESTION"));
                @SuppressLint("Range") String answer = res.getString(res.getColumnIndex("ANSWER"));
                @SuppressLint("Range") String type = res.getString(res.getColumnIndex("TYPE"));
                @SuppressLint("Range") String correct = res.getString(res.getColumnIndex("CORRECT"));

                JSONObject contentObj = new JSONObject();
                contentObj.put("id", id);
                contentObj.put("parent", "0");
                contentObj.put("content", question);
                contentObj.put("title", "");
                contentObj.put("type", type);
                contentObj.put("answer", answer);
                contentObj.put("correct", correct);
                contentObj.put("question_image", "");
                arr.put(contentObj);

            }
            qObj.put("0", arr);
            jsonObject.put("q", qObj);
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray.toString();
    }


}
