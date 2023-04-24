package com.example.carapp.General_Singletons;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import com.example.carapp.R;

public class SignalGenerator
{

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Variables

    @SuppressLint("StaticFieldLeak")
    private static SignalGenerator instance = null;
    private final Context context;
    private static Vibrator vibrator;
    private static MediaPlayer mediaPlayer;

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Constructor and initializer

    private SignalGenerator(Context context)
    {
        this.context = context;
    }

    public static void init(Context context)
    {
        if (instance == null) {
            instance = new SignalGenerator(context);
            vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            mediaPlayer = MediaPlayer.create(context, R.raw.crash_sound);
        }
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Getter

    public static SignalGenerator getInstance()
    {
        return instance;
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //General functions

    public void playCrash(Context context)
    {
        if (mediaPlayer ==null)
        {
            mediaPlayer = MediaPlayer.create(context, R.raw.crash_sound);
        }
        mediaPlayer.start();
    }

    public void toast(String text, int length)
    {
        Toast.makeText(context, text, length).show();
    }

    @SuppressLint("ObsoleteSdkInt")
    public void vibrate(long length){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            vibrator.vibrate(VibrationEffect.createOneShot(length, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(length);
        }
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
}