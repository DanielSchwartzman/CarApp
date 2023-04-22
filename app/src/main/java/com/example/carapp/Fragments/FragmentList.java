package com.example.carapp.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.carapp.Data.MySP3;
import com.example.carapp.R;

import java.util.Objects;

public class FragmentList extends Fragment
{
    public static final String SHARED_PREFS="sharedPrefs";
    public static final String SCORE ="score";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        TextView txt=view.findViewById(R.id.textView2);
        loadData(txt);
        return view;
    }

    private void loadData(TextView txt)
    {
        txt.setText(MySP3.getInstance().getString(SCORE,""));
    }

}