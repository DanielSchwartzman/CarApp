package com.example.carapp.General_Singletons;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import com.example.carapp.HighScore.HighScoreList;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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

    private static FusedLocationProviderClient fusedLocationProviderClient;
    private boolean permission=false;

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Constructor and initialization

    private MySP(Context context)
    {
        sharedPreferences = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
    }

    public static void init(Context context)
    {
        if (instance == null){
            instance = new MySP(context);
            String fromSP =  MySP.getInstance().getString("leaderBoard","");
            if(!fromSP.equals(""))
                highScoreList=new Gson().fromJson(fromSP, HighScoreList.class);
            else
            {
                highScoreList = new HighScoreList();
            }
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        }
    }

    public static MySP getInstance()
    {
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
    //Location methods

    public static HighScoreList getHighScoreList()
    {
        return highScoreList;
    }

    public static void saveToSharedPref()
    {
        String toSp=new Gson().toJson(highScoreList);
        MySP.getInstance().putString("leaderBoard",toSp);
    }

    public void checkPermission(Activity activity)
    {
        if(ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            permission=true;
        }
        else
        {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
            permission=true;//Assumption: Permission granted
        }
    }

    @SuppressLint("MissingPermission")
    public void addNewHighScore(int score)
    {
        if(permission)
        {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task ->
            {
                Location location = task.getResult();
                if (location != null) {
                    highScoreList.addNewHighScore(score, location.getLongitude(), location.getLatitude());
                }
                else
                {
                    SignalGenerator.getInstance().toast("Please turn on location", Toast.LENGTH_LONG);
                }
            });
        }
        else
        {
            SignalGenerator.getInstance().toast("Location permission not granted", Toast.LENGTH_LONG);
        }
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
}