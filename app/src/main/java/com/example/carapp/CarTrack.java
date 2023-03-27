package com.example.carapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CarTrack extends AppCompatActivity {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Variables

    ArrayList<ImageView> hearts=new ArrayList<>();
    ImageView[] carImages=new ImageView[3];
    int[] carLocationArray=new int[3];

    ImageView[][] obstacleImages=new ImageView[6][3];
    int[][] rockLocationMatrix=new int[6][3];

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //OnCreate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_track);
        initializeView();

        startTimer();
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Timer methods

    private void startTimer()
    {
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run()
            {
                runOnUiThread(() -> {
                    moveRocks();
                    addRock();
                    displayGameView();
                });
            }
        }, 0, 500);
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Remove heart method

    private void removeHeart()
    {
        if(hearts.size()>0)
        {
            hearts.get(hearts.size()-1).setVisibility(View.INVISIBLE);
            hearts.remove(hearts.size()-1);
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            Toast.makeText(getApplicationContext(),"Crash",Toast.LENGTH_SHORT).show();
        }
    }
    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Move car methods

    private void moveCarLeft()
    {
        if(carLocationArray[1]==1)
        {
            carLocationArray[1]=0;
            carLocationArray[0]=1;
        }
        else if(carLocationArray[2]==1)
        {
            carLocationArray[2]=0;
            carLocationArray[1]=1;
        }
    }

    private void moveCarRight()
    {
        if(carLocationArray[0]==1)
        {
            carLocationArray[0]=0;
            carLocationArray[1]=1;
        }
        else if(carLocationArray[1]==1)
        {
            carLocationArray[1]=0;
            carLocationArray[2]=1;
        }
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Display game view methods

    private void displayGameView()
    {
        displayCar();
        displayRocks();
    }

    private void displayCar()
    {
        for (int i = 0; i < 3; i++)
        {
            if(carLocationArray[i]==0)
            {
                carImages[i].setVisibility(View.INVISIBLE);
            }
            else
            {
                carImages[i].setVisibility(View.VISIBLE);
            }
        }
    }

    private void displayRocks()
    {
        for (int i = 0; i < rockLocationMatrix.length; i++)
        {
            for (int j = 0; j < rockLocationMatrix[0].length; j++)
            {
                if(rockLocationMatrix[i][j]==0)
                {
                    obstacleImages[i][j].setVisibility(View.INVISIBLE);
                }
                else
                {
                    obstacleImages[i][j].setVisibility(View.VISIBLE);
                }
            }
        }
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Add rocks methods

    private void addRock()
    {
        int whereToPutRock=(int)(Math.random()*3);
        if(isLaneFree(whereToPutRock))
        {
            rockLocationMatrix[0][whereToPutRock]=1;
        }
    }

    private boolean isLaneFree(int laneNum)
    {
        for (int[] locationMatrix : rockLocationMatrix) {
            if (locationMatrix[laneNum] == 1)
                return false;
        }
        return true;
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Move rocks methods

    private void moveRocks()
    {
        moveRocksOnLeftLane();
        moveRocksOnMiddleLane();
        moveRocksOnRightLane();
    }

    private void moveRocksOnLeftLane()
    {
        for (int i = 0; i < rockLocationMatrix.length ; i++)
        {
            if(rockLocationMatrix[i][0]==1)
            {
                if(i<rockLocationMatrix.length-1)
                {
                    rockLocationMatrix[i][0]=0;
                    rockLocationMatrix[i+1][0]=1;
                }
                else
                {
                    checkForImpact(0);
                    rockLocationMatrix[i][0]=0;
                }
                return;
            }
        }
    }

    private void moveRocksOnMiddleLane()
    {
        for (int i = 0; i < rockLocationMatrix.length ; i++)
        {
            if(rockLocationMatrix[i][1]==1)
            {
                if(i<rockLocationMatrix.length-1)
                {
                    rockLocationMatrix[i][1]=0;
                    rockLocationMatrix[i+1][1]=1;
                }
                else
                {
                    checkForImpact(1);
                    rockLocationMatrix[i][1]=0;
                }
                return;
            }
        }
    }

    private void moveRocksOnRightLane()
    {
        for (int i = 0; i < rockLocationMatrix.length ; i++)
        {
            if(rockLocationMatrix[i][2]==1)
            {
                if(i<rockLocationMatrix.length-1)
                {
                    rockLocationMatrix[i][2]=0;
                    rockLocationMatrix[i+1][2]=1;
                }
                else
                {
                    checkForImpact(2);
                    rockLocationMatrix[i][2]=0;
                }
                return;
            }
        }
    }

    private void checkForImpact(int lane)
    {
        int len=rockLocationMatrix.length-1;
        if((rockLocationMatrix[len][lane]==1)&&(carLocationArray[lane]==1))
        {
            removeHeart();
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
        initializeCar();
        initializeMovement();
        initializeObstacles();
    }

    private void initializeHearths()
    {
        hearts.add(findViewById(R.id.CT_IV_heart1));
        hearts.add(findViewById(R.id.CT_IV_heart2));
        hearts.add(findViewById(R.id.CT_IV_heart3));
    }

    private void initializeMovement()
    {
        ImageButton moveLeft=findViewById(R.id.CT_IB_Left);
        ImageButton moveRight=findViewById(R.id.CT_IB_Right);
        moveLeft.setOnClickListener(view->
        {
            moveCarLeft();
            displayGameView();
        });
        moveRight.setOnClickListener(view->
        {
            moveCarRight();
            displayGameView();
        });
    }

    private void initializeCar()
    {
        carLocationArray[0]=0;
        carLocationArray[1]=1;
        carLocationArray[2]=0;
        carImages[0]=findViewById(R.id.CT_IV_CarLeft);
        carImages[1]=findViewById(R.id.CT_IV_CarMiddle);
        carImages[2]=findViewById(R.id.CT_IV_CarRight);
    }

    private void initializeObstacles()
    {
        initializeFirstImageRow();
        initializeSecondImageRow();
        initializeThirdImageRow();
        initializeFourthImageRow();
        initializeFifthImageRow();
        initializeSixthImageRow();
    }

    private void initializeFirstImageRow()
    {
        obstacleImages[0][0]=findViewById(R.id.CT_IV_Obstacle00);
        obstacleImages[0][1]=findViewById(R.id.CT_IV_Obstacle01);
        obstacleImages[0][2]=findViewById(R.id.CT_IV_Obstacle02);
    }

    private void initializeSecondImageRow()
    {
        obstacleImages[1][0]=findViewById(R.id.CT_IV_Obstacle10);
        obstacleImages[1][1]=findViewById(R.id.CT_IV_Obstacle11);
        obstacleImages[1][2]=findViewById(R.id.CT_IV_Obstacle12);
    }

    private void initializeThirdImageRow()
    {
        obstacleImages[2][0]=findViewById(R.id.CT_IV_Obstacle20);
        obstacleImages[2][1]=findViewById(R.id.CT_IV_Obstacle21);
        obstacleImages[2][2]=findViewById(R.id.CT_IV_Obstacle22);
    }

    private void initializeFourthImageRow()
    {
        obstacleImages[3][0]=findViewById(R.id.CT_IV_Obstacle30);
        obstacleImages[3][1]=findViewById(R.id.CT_IV_Obstacle31);
        obstacleImages[3][2]=findViewById(R.id.CT_IV_Obstacle32);
    }

    private void initializeFifthImageRow()
    {
        obstacleImages[4][0]=findViewById(R.id.CT_IV_Obstacle40);
        obstacleImages[4][1]=findViewById(R.id.CT_IV_Obstacle41);
        obstacleImages[4][2]=findViewById(R.id.CT_IV_Obstacle42);
    }

    private void initializeSixthImageRow()
    {
        obstacleImages[5][0]=findViewById(R.id.CT_IV_Obstacle50);
        obstacleImages[5][1]=findViewById(R.id.CT_IV_Obstacle51);
        obstacleImages[5][2]=findViewById(R.id.CT_IV_Obstacle52);
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
}