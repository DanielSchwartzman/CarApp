package com.example.carapp.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.fragment.app.Fragment;
import com.example.carapp.R;

public class FragmentMap extends Fragment
{
    private WebView webView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.fragment_map, container, false);
        initializeWebView(view);
        setWebUrl(32.0853,34.7818);
        return view;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initializeWebView(View view)
    {
        webView =view.findViewById(R.id.Frag_WEBV_webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    public void setWebUrl(double longitude,double latitude)
    {
        String urlStart = "https://www.google.com/maps/place/";
        webView.loadUrl(urlStart +longitude+","+latitude);
    }

}