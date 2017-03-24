package trigues.com.trueke.view.impl;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import trigues.com.trueke.R;


public class RegisterFragImpl extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_register);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);


        return view;
    }
}
