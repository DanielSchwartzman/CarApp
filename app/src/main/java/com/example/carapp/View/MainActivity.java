package com.example.carapp.View;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.carapp.R;
import com.example.carapp.View.CarTrack;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeButtons();
    }

    private void initializeButtons()
    {
        Button startGame=findViewById(R.id.main_btn_startGame);
        startGame.setOnClickListener(view->
        {
            Intent switchToCarActivity=new Intent(getApplicationContext(), CarTrack.class);
            startActivity(switchToCarActivity);
        });
    }
}