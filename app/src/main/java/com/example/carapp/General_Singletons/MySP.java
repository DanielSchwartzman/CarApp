package com.example.carapp.General_Singletons;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.carapp.HighScore.HighScoreList;
import com.google.gson.Gson;

public class MySP
{

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Variables

    private static final String DB_FILE = "DB_FILE";
    private static MySP instance = null;
    private static SharedPreferences sharedPreferences = null;
    private static HighScoreList highScoreList;

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Constructor and initialization

    private MySP(Context context)
    {
        sharedPreferences = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
    }

    public static void init(Context context){
        if (instance == null){
            instance = new MySP(context);
            String fromSP =  MySP.getInstance().getString("leaderBoard","");
            if(!fromSP.equals(""))
                highScoreList=new Gson().fromJson(fromSP, HighScoreList.class);
            else
            {
                highScoreList = new HighScoreList();
            }
        }
    }

    public static MySP getInstance() {
        return instance;
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //String methods

    public void putString(String key, String value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String value)
    {
        return sharedPreferences.getString(key, value);
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Variables

    public static HighScoreList getHighScoreList()
    {
        return highScoreList;
    }

    public static void saveToSharedPref()
    {
        String toSp=new Gson().toJson(highScoreList);
        MySP.getInstance().putString("leaderBoard",toSp);
    }

    public static void addNewHighScore(int score, double longitude, double latitude)
    {
        highScoreList.addNewHighScore(score,longitude,latitude);
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
}