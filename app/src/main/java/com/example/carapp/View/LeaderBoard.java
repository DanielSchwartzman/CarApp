package com.example.carapp.View;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.carapp.Fragments.FragmentList;
import com.example.carapp.Fragments.FragmentMap;
import com.example.carapp.R;

public class LeaderBoard extends AppCompatActivity {

    private FragmentList listView;
    private FragmentMap mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        initializeFragments();
        beginTransaction();
    }

    private void initializeFragments()
    {
        listView=new FragmentList();
        mapView=new FragmentMap();
    }

    private void beginTransaction()
    {
        getSupportFragmentManager().beginTransaction().add(R.id.leaderBoard_FRAME_list, listView).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.leaderBoard_FRAME_map, mapView).commit();
    }
}