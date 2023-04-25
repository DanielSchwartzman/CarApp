package com.example.carapp.View;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.carapp.General_Singletons.MySP;
import com.example.carapp.Model.GameManager;
import com.example.carapp.R;
import com.example.carapp.General_Singletons.SignalGenerator;
import java.util.Timer;
import java.util.TimerTask;

public class CarTrack extends AppCompatActivity {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Variables

    //Basic
    ImageView[] hearts = new ImageView[3];
    ImageView[][] allImages = new ImageView[8][5];
    GameManager model;
    Timer timer = null;
    String gameMode;
    int currentLife;
    TextView score;

    //Sensors
    SensorManager sensorManager;
    Sensor gyroSensor;
    private SensorEventListener gyroEventListener;
    private float rollAngle = 0.0f;

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //OnCreate/OnPause

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_track);
        gameMode = getIntent().getStringExtra("GameMode");
        initializeView();
        model = new GameManager(this);

        chooseTimer();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        MySP.saveToSharedPref();
        if (timer != null)
        {
            timer.cancel();
            timer = null;
        }
        if (sensorManager != null)
            sensorManager.unregisterListener(gyroEventListener);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (timer == null)
        {
            chooseTimer();
        }
        if (sensorManager != null)
            sensorManager.registerListener(gyroEventListener, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void chooseTimer() {
        switch (gameMode) {
            case "Slow": {
                startTimerSlow();
                break;
            }
            case "Medium": {
                startTimerMedium();
                break;
            }
            case "Fast": {
                startTimerFast();
                break;
            }
            case "Sensor": {
                initializeSensor();
                startTimerSensor();
                break;
            }
        }
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Sensor methods

    private void initializeSensor()
    {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if (gyroSensor == null) {
            Toast.makeText(this, "Gyroscope sensor is not available on the device", Toast.LENGTH_SHORT).show();
            finish();
        }

        gyroEventListener = new SensorEventListener()
        {
            @Override
            public void onSensorChanged(SensorEvent event)
            {
                if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE)
                {
                    float deltaRoll = event.values[2] * (180 / (float) Math.PI) * (event.timestamp - rollAngle) / 1000000000.0f;
                    rollAngle = event.timestamp;

                    if (deltaRoll < -2.0f) {
                        model.moveCarRight();
                        displayGameView();
                    } else if (deltaRoll > 2.0f) {
                        model.moveCarLeft();
                        displayGameView();
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy)
            {
                //Do nothing
            }
        };
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Timer methods

    private void startTimerSlow()
    {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() ->
                {
                    model.timedFunctions();
                    displayGameView();
                });
            }
        }, 0, 1000);
    }

    private void startTimerMedium()
    {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() ->
                {
                    model.timedFunctions();
                    displayGameView();
                });
            }
        }, 0, 500);
    }

    private void startTimerFast()
    {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() ->
                {
                    model.timedFunctions();
                    displayGameView();
                });
            }
        }, 0, 200);
    }

    private void startTimerSensor()
    {
        LinearLayout linearLayout = findViewById(R.id.CT_LL_movement);
        linearLayout.setVisibility(View.GONE);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                runOnUiThread(() ->
                {
                    model.timedFunctions();
                    displayGameView();
                });
            }
        }, 0, 1000);
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Remove heart/score method

    public void removeHeart()
    {
        if (currentLife > 0)
        {
            hearts[currentLife - 1].setVisibility(View.GONE);
            currentLife--;
            SignalGenerator.getInstance().toast("Crash",Toast.LENGTH_SHORT);
            SignalGenerator.getInstance().vibrate(500);
            SignalGenerator.getInstance().playCrash(this);
        }
        if(currentLife<=0)
        {
            MySP.getInstance().addNewHighScore(model.getDistance());
            MySP.saveToSharedPref();
            Intent switchToLeaderBoardActivity = new Intent(getApplicationContext(), LeaderBoard.class);
            startActivity(switchToLeaderBoardActivity);
            finish();
        }
    }

    public void addHeart() {
        if (currentLife < 3) {
            hearts[currentLife].setVisibility(View.VISIBLE);
            currentLife++;
        }
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Display game view methods

    private void displayGameView() {
        int[][] rockLocationMatrix = model.getGameBoardMatrix();
        String scoreText = model.getDistance() + "";
        score.setText(scoreText);
        for (int i = 0; i < rockLocationMatrix.length; i++) {
            for (int j = 0; j < rockLocationMatrix[0].length; j++) {
                if (rockLocationMatrix[i][j] == 0)//Void
                {
                    allImages[i][j].setBackgroundResource(android.R.color.transparent);
                } else if (rockLocationMatrix[i][j] == 1)//Car
                {
                    allImages[i][j].setBackgroundResource(R.drawable.car);
                } else if (rockLocationMatrix[i][j] == 2)//Rock
                {
                    allImages[i][j].setBackgroundResource(R.drawable.obstacle);
                } else if (rockLocationMatrix[i][j] == 3)//Special
                {
                    allImages[i][j].setBackgroundResource(R.drawable.special);
                }
            }
        }
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Initialize view methods

    private void initializeView()
    {
        initializeHearths();
        initializeMovement();
        initializeObstaclesAndCars();
        score=findViewById(R.id.carTrack_TXT_score);
    }

    private void initializeHearths()
    {
        hearts[0]=findViewById(R.id.CT_IV_heart1);
        hearts[1]=findViewById(R.id.CT_IV_heart2);
        hearts[2]=findViewById(R.id.CT_IV_heart3);
        currentLife=3;
    }

    private void initializeMovement()
    {
        ImageButton moveLeft=findViewById(R.id.CT_IB_Left);
        ImageButton moveRight=findViewById(R.id.CT_IB_Right);
        moveLeft.setOnClickListener(view->
        {
            model.moveCarLeft();
            displayGameView();
        });
        moveRight.setOnClickListener(view->
        {
            model.moveCarRight();
            displayGameView();
        });
    }

    private void initializeObstaclesAndCars()
    {
        initializeFirstImageRow();
        initializeSecondImageRow();
        initializeThirdImageRow();
        initializeFourthImageRow();
        initializeFifthImageRow();
        initializeSixthImageRow();
        initializeSeventhImageRow();
        initializeEighthImageRow();
    }

    private void initializeFirstImageRow()
    {
        allImages[0][0]=findViewById(R.id.CT_IV_Obstacle00);
        allImages[0][1]=findViewById(R.id.CT_IV_Obstacle01);
        allImages[0][2]=findViewById(R.id.CT_IV_Obstacle02);
        allImages[0][3]=findViewById(R.id.CT_IV_Obstacle03);
        allImages[0][4]=findViewById(R.id.CT_IV_Obstacle04);
    }

    private void initializeSecondImageRow()
    {
        allImages[1][0]=findViewById(R.id.CT_IV_Obstacle10);
        allImages[1][1]=findViewById(R.id.CT_IV_Obstacle11);
        allImages[1][2]=findViewById(R.id.CT_IV_Obstacle12);
        allImages[1][3]=findViewById(R.id.CT_IV_Obstacle13);
        allImages[1][4]=findViewById(R.id.CT_IV_Obstacle14);
    }

    private void initializeThirdImageRow()
    {
        allImages[2][0]=findViewById(R.id.CT_IV_Obstacle20);
        allImages[2][1]=findViewById(R.id.CT_IV_Obstacle21);
        allImages[2][2]=findViewById(R.id.CT_IV_Obstacle22);
        allImages[2][3]=findViewById(R.id.CT_IV_Obstacle23);
        allImages[2][4]=findViewById(R.id.CT_IV_Obstacle24);
    }

    private void initializeFourthImageRow()
    {
        allImages[3][0]=findViewById(R.id.CT_IV_Obstacle30);
        allImages[3][1]=findViewById(R.id.CT_IV_Obstacle31);
        allImages[3][2]=findViewById(R.id.CT_IV_Obstacle32);
        allImages[3][3]=findViewById(R.id.CT_IV_Obstacle33);
        allImages[3][4]=findViewById(R.id.CT_IV_Obstacle34);
    }

    private void initializeFifthImageRow()
    {
        allImages[4][0]=findViewById(R.id.CT_IV_Obstacle40);
        allImages[4][1]=findViewById(R.id.CT_IV_Obstacle41);
        allImages[4][2]=findViewById(R.id.CT_IV_Obstacle42);
        allImages[4][3]=findViewById(R.id.CT_IV_Obstacle43);
        allImages[4][4]=findViewById(R.id.CT_IV_Obstacle44);
    }

    private void initializeSixthImageRow()
    {
        allImages[5][0]=findViewById(R.id.CT_IV_Obstacle50);
        allImages[5][1]=findViewById(R.id.CT_IV_Obstacle51);
        allImages[5][2]=findViewById(R.id.CT_IV_Obstacle52);
        allImages[5][3]=findViewById(R.id.CT_IV_Obstacle53);
        allImages[5][4]=findViewById(R.id.CT_IV_Obstacle54);
    }

    private void initializeSeventhImageRow()
    {
        allImages[6][0]=findViewById(R.id.CT_IV_Obstacle60);
        allImages[6][1]=findViewById(R.id.CT_IV_Obstacle61);
        allImages[6][2]=findViewById(R.id.CT_IV_Obstacle62);
        allImages[6][3]=findViewById(R.id.CT_IV_Obstacle63);
        allImages[6][4]=findViewById(R.id.CT_IV_Obstacle64);
    }

    private void initializeEighthImageRow()
    {
        allImages[7][0]=findViewById(R.id.CT_IV_Obstacle70);
        allImages[7][1]=findViewById(R.id.CT_IV_Obstacle71);
        allImages[7][2]=findViewById(R.id.CT_IV_Obstacle72);
        allImages[7][3]=findViewById(R.id.CT_IV_Obstacle73);
        allImages[7][4]=findViewById(R.id.CT_IV_Obstacle74);
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
}