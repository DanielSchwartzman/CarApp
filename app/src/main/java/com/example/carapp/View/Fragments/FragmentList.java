package com.example.carapp.View.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carapp.General_Singletons.MySP;
import com.example.carapp.R;
import com.example.carapp.View.RecycleContents.HighScoreAdapter;

public class FragmentList extends Fragment
{

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Variables

    RecyclerView recyclerView;

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //On create and initialization

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initViews();
        return view;
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////




    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////
    //Find and initialize views

    private void findViews(View view)
    {
        recyclerView=view.findViewById(R.id.listFrag_REC_recView);
    }

    private void initViews()
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(super.getContext()));
        recyclerView.setAdapter(new HighScoreAdapter(super.getContext(), MySP.getHighScoreList()));
    }

    //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

}