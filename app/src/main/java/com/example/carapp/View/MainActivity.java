package com.example.carapp.View;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.carapp.General_Singletons.MySP;
import com.example.carapp.General_Singletons.SignalGenerator;
import com.example.carapp.R;

public class MainActivity extends AppCompatActivity
{

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Variables

    String gameMode;
    RadioGroup radioGroup;
    RadioButton radioButton;

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //On create and initialization method

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MySP.init(this);
        SignalGenerator.init(this);
        radioGroup=findViewById(R.id.main_RGP_radioGroup);
        initializeButtons();
        MySP.getInstance().checkPermission(this);
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Buttons methods

    private void initializeButtons()
    {
        Button startGame=findViewById(R.id.main_BTN_startGame);
        Button leaderBoard=findViewById(R.id.main_BTN_leaderBoard);
        startGame.setOnClickListener(view->
        {
            if(checkButton())
            {
                Intent switchToCarActivity=new Intent(getApplicationContext(), CarTrack.class);
                switchToCarActivity.putExtra("GameMode",gameMode);
                startActivity(switchToCarActivity);
            }
            else
            {
                Toast.makeText(this, "Please select game mode", Toast.LENGTH_SHORT).show();
            }
        });
        leaderBoard.setOnClickListener(view->
        {
            Intent switchToLeaderBoardActivity=new Intent(getApplicationContext(), LeaderBoard.class);
            startActivity(switchToLeaderBoardActivity);
        });
    }

    public boolean checkButton()
    {
        int radioID=radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(radioID);
        if(radioButton!=null)
        {
            gameMode = radioButton.getText().toString();
            return true;
        }
        else
        {
            return false;
        }
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
}