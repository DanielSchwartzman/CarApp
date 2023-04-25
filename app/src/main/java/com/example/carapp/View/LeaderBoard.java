package com.example.carapp.View;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.carapp.General_Singletons.SignalGenerator;
import com.example.carapp.View.Fragments.FragmentList;
import com.example.carapp.View.Fragments.FragmentMap;
import com.example.carapp.R;

public class LeaderBoard extends AppCompatActivity
{

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Variables

    private FragmentList listView;
    private FragmentMap mapView;

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //On create and initialization

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        initializeFragments();
        beginTransaction();
    }

    private void initializeFragments()
    {
        listView=new FragmentList();
        mapView=new FragmentMap();
        SignalGenerator.getInstance().setFragmentMap(mapView);
    }

    private void beginTransaction()
    {
        getSupportFragmentManager().beginTransaction().add(R.id.leaderBoard_FRAME_list, listView).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.leaderBoard_FRAME_map, mapView).commit();
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
}