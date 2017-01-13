package com.fragment.admin.retrofitdemo.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fragment.admin.retrofitdemo.R;
import com.fragment.admin.retrofitdemo.view.fragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, new fragment()).commit();
        }
    }
}
