package com.example.carapp.Data;

public class HighScore
{
    int score;
    double longitude;
    double latitude;

    public HighScore(int score,double longitude,double latitude)
    {
        this.score=score;
        this.longitude=longitude;
        this.latitude=latitude;
    }

    public boolean isHigherThan(int otherScore)
    {
        return (score>=otherScore);
    }
}
