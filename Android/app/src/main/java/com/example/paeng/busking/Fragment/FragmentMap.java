package com.example.paeng.busking.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.paeng.busking.R;

import net.daum.mf.map.api.MapView;


public class FragmentMap extends Fragment {

    public static final String TITLE = "Map";

    public static FragmentMap newInstance() {

        return new FragmentMap();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_map, container, false);
        /*
        MapView mapView = new MapView(getActivity());

        ViewGroup mapViewContainer = (ViewGroup)mView.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        */

        return mView;
    }
}