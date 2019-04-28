package com.arayan.stackedcups.controller;

import android.os.Bundle;

import com.arayan.stackedcups.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private View holderView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_fill_cups:
                        showCupsView();
                        return true;
                    case R.id.navigation_query:
                        showQueryView();
                        return true;
                    case R.id.navigation_about:
                        showAboutView();
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        holderView = findViewById(R.id.holder_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void showCupsView() {
        removeAllChildViews();
    }

    private void showQueryView() {
        removeAllChildViews();
    }

    private void showAboutView() {
        removeAllChildViews();
    }

    private void removeAllChildViews() {
        if (holderView != null && holderView instanceof ViewGroup) {
            ((ViewGroup)holderView).removeAllViews();
        }
        else {
            final Toast toast = Toast.makeText(this, getString(R.string.navigation_failure), Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
