package com.example.jason.testing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by chris on 3/25/16.
 */
public class CustomMapFragment extends SupportMapFragment implements View.OnClickListener {

    public CustomMapFragment()
    {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public static CustomMapFragment newInstance() {
        CustomMapFragment fragment = new CustomMapFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        Fragment fragment = getParentFragment();
        if (fragment != null && fragment instanceof OnMapReadyListener) {
            ((OnMapReadyListener) fragment).onMapReady();
        }

        return rootView;
    }

    @Override
    public void onClick(View v) {

    }


    /**
     * Listener interface to tell when the map is ready
     */
    public static interface OnMapReadyListener {

        void onMapReady();
    }

}