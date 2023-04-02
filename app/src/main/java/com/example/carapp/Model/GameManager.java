package com.example.carapp.Model;

import com.example.carapp.View.CarTrack;

public class GameManager
{

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Variables

    private final CarTrack view;
    private final int[] carLocationArray=new int[3];
    private final int[][] rockLocationMatrix=new int[6][3];

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Constructor

    public GameManager(CarTrack view)
    {
        this.view=view;
        carLocationArray[0]=0;
        carLocationArray[1]=1;
        carLocationArray[2]=0;
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Getters

    public int[] getCarLocationArray()
    {
        return carLocationArray;
    }

    public int[][] getRockLocationMatrix()
    {
        return rockLocationMatrix;
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Move rocks methods

    public void moveRocks()
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
            view.removeHeart();
        }
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Move car methods

    public void moveCarLeft()
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

    public void moveCarRight()
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
    //Add rocks methods

    public void addRock()
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

}
