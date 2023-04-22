package com.example.carapp.Model;

import com.example.carapp.View.CarTrack;

import java.util.Arrays;

public class GameManager
{

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Variables

    private final CarTrack view;
    private final int[][] gameBoardMatrix = new int[8][5];

    //Current Location of Obstacles
    private final int[] obstacleLocation=new int[5];

    //Current Location of Car
    private int carCurrentLocation;

    //Current Location of Special
    private int specialLocationRow;
    private int specialLocationCol;

    //Odometer
    private int distance;

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Constructor

    public GameManager(CarTrack view)
    {
        this.view=view;
        initializeCar();
        initializeObstacleLocation();
        initializeSpecial();
        distance=0;
    }

    private void initializeCar()
    {
        gameBoardMatrix[7][0]=0;
        gameBoardMatrix[7][1]=0;
        gameBoardMatrix[7][2]=1;
        gameBoardMatrix[7][3]=0;
        gameBoardMatrix[7][4]=0;
        carCurrentLocation=2;
    }

    private void initializeObstacleLocation()
    {
        Arrays.fill(obstacleLocation, -1);
    }

    private void initializeSpecial()
    {
        specialLocationRow=-1;
        specialLocationCol=-1;
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Getters

    public int[][] getGameBoardMatrix()
    {
        return gameBoardMatrix;
    }
    public int getDistance(){return distance;}

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Timer activation method

    public void timedFunctions()
    {
        moveObstacles();
        moveSpecial();
        addObstacle();
        addSpecial();
        distance+=10;
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Move rocks methods

    private void moveObstacles()
    {
        for (int i = 0; i < obstacleLocation.length; i++)
        {
            if(obstacleLocation[i]==gameBoardMatrix.length-1)//Obstacle reached end
            {
                gameBoardMatrix[obstacleLocation[i]][i] = 0;
                obstacleLocation[i]=-1;
            }
            if(obstacleLocation[i]==gameBoardMatrix.length-2)//Obstacle can impact the car
            {
                if(checkForImpactWithObstacle(i))
                {
                    gameBoardMatrix[obstacleLocation[i]][i]=0;
                    obstacleLocation[i]=-1;
                }
            }
            if(obstacleLocation[i]!=-1)//Move obstacle
            {
                gameBoardMatrix[obstacleLocation[i]++][i] = 0;
                gameBoardMatrix[obstacleLocation[i]][i] = 2;
            }
        }
    }

    private boolean checkForImpactWithObstacle(int lane)
    {
        if(gameBoardMatrix[7][lane]==1)
        {
            view.removeHeart();
            return true;
        }
        return false;
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Move special methods

    private void moveSpecial()
    {
        if(specialLocationRow==gameBoardMatrix.length-1)//Special reached end
        {
            conditionalRemoveSpecial();
            gameBoardMatrix[specialLocationRow][specialLocationCol]=0;
            initializeSpecial();
        }
        if (specialLocationRow==gameBoardMatrix.length-2)
        {
            if(checkForImpactWithSpecial())
            {
                conditionalRemoveSpecial();
                initializeSpecial();
            }
        }
        if((specialLocationRow!=-1)&&(specialLocationCol!=-1))//Move special
        {
            conditionalRemoveSpecial();
            gameBoardMatrix[++specialLocationRow][specialLocationCol]=3;
        }
    }

    private boolean checkForImpactWithSpecial()
    {
        if(gameBoardMatrix[specialLocationRow+1][specialLocationCol]==1)
        {
            view.addHeart();
            return true;
        }
        return false;
    }

    private void conditionalRemoveSpecial()//checks the possibility that an obstacle has overlapped the current location of Special
    {
        if(gameBoardMatrix[specialLocationRow][specialLocationCol]==3)
        {
            gameBoardMatrix[specialLocationRow][specialLocationCol]=0;
        }
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Move car methods

    public void moveCarLeft()
    {
        if(carCurrentLocation!=0)
        {
            checkCarMovementLeft();
            gameBoardMatrix[7][carCurrentLocation--]=0;
            gameBoardMatrix[7][carCurrentLocation]=1;
        }
    }

    public void moveCarRight()
    {
        if(carCurrentLocation!=4)
        {
            checkCarMovementRight();
            gameBoardMatrix[7][carCurrentLocation++]=0;
            gameBoardMatrix[7][carCurrentLocation]=1;
        }
    }

    private void checkCarMovementLeft()
    {
        if(gameBoardMatrix[7][carCurrentLocation-1]==2)
        {
            gameBoardMatrix[7][carCurrentLocation-1]=0;
            obstacleLocation[carCurrentLocation-1]=-1;
            view.removeHeart();
        }
        else if(gameBoardMatrix[7][carCurrentLocation-1]==3)
        {
            conditionalRemoveSpecial();
            initializeSpecial();
            view.addHeart();
        }
    }

    private void checkCarMovementRight()
    {
        if(gameBoardMatrix[7][carCurrentLocation+1]==2)
        {
            gameBoardMatrix[7][carCurrentLocation+1]=0;
            obstacleLocation[carCurrentLocation+1]=-1;
            view.removeHeart();
        }
        else if(gameBoardMatrix[7][carCurrentLocation+1]==3)
        {
            conditionalRemoveSpecial();
            initializeSpecial();
            view.addHeart();
        }
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Add Obstacle and Special methods

    private void addObstacle()
    {
        int randomLocation=(int)(Math.random()*5);
        if(obstacleLocation[randomLocation]==-1)
        {
            gameBoardMatrix[0][randomLocation]=2;
            obstacleLocation[randomLocation]=0;
        }
    }

    private void addSpecial()
    {
        if(((specialLocationRow==-1)&&(specialLocationCol==-1))&&((distance%200==0)&&(distance!=0)))
        {
            int randomLocation=(int)(Math.random()*5);
            if(obstacleLocation[randomLocation]!=0)
            {
                specialLocationRow=0;
                specialLocationCol=randomLocation;
                gameBoardMatrix[specialLocationRow][specialLocationCol]=3;
            }
        }
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    
}
