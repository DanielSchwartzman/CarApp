package com.example.carapp.HighScore;

import java.util.ArrayList;
import java.util.Comparator;

public class HighScoreList
{

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Variables

    ArrayList<HighScore> allScores;

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Constructor

    public HighScoreList()
    {
        allScores=new ArrayList<>();
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Check and Add new high score

    public  void addNewHighScore(int score,double longitude,double latitude)
    {
        if(allScores.size()==0)
        {
            allScores.add(new HighScore(score, longitude, latitude));
        }
        else
        {
            int size=allScores.size();
            for (int i = 0; i < size; i++)
            {
                if (allScores.get(i).otherGreater(score))
                {
                    allScores.add(new HighScore(score, longitude, latitude));
                    break;
                }
            }
        }

        allScores.sort(new HighsScoreComparator());
        if(allScores.size()==12)
        {
            allScores.remove(allScores.size()-1);
        }
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Getter

    public ArrayList<HighScore> getAllScores()
    {
        return allScores;
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //inner Comparator class for sorting

    static class HighsScoreComparator implements Comparator<HighScore>
    {

        @Override
        public int compare(HighScore o1, HighScore o2)
        {
            return o2.score-o1.score;
        }
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

}