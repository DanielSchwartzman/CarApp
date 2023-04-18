package com.example.carapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.carapp.R;
import com.google.android.gms.maps.MapView;

public class FragmentMap extends Fragment
{
    private MapView mapView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.fragment_map, container, false);
        mapView=view.findViewById(R.id.mapView);
        return view;
    }

}