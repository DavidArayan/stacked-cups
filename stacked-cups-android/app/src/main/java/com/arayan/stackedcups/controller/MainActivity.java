package com.arayan.stackedcups.controller;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.arayan.stackedcups.R;
import com.arayan.stackedcups.model.CupStackLUT;
import com.arayan.stackedcups.model.WhiskeyCup;
import com.arayan.stackedcups.model.exceptions.InvalidArgumentException;
import com.arayan.stackedcups.model.interfaces.CupLUT;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ViewGroup holderView;
    private CupLUT stackedCups;

    // our page views, inflated during onCreate
    private View pageAbout;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_fill_cups:
                        showPage(null);
                        return true;
                    case R.id.navigation_query:
                        showPage(null);
                        return true;
                    case R.id.navigation_about:
                        showPage(pageAbout);
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

        // inflate our page views for the user
        inflatePages();

        try {
            stackedCups = new CupStackLUT((row, col) -> new WhiskeyCup(250, row, col));
            //showPage(null);
        }
        catch (final InvalidArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Inflate all our navigation pages, we will push/pop these pages to/from
     * the stack
     */
    @SuppressLint("InflateParams")
    private void inflatePages() {
        this.pageAbout = getLayoutInflater().inflate(R.layout.page_about, null);
    }

    private void showPage(@NonNull final View page) {
        removeAllChildViews();

        if (checkValidity()) {
            holderView.addView(page);
        }
    }

    /**
     * Removes any previews views from the stack
     */
    private void removeAllChildViews() {
        if (holderView != null) {
            holderView.removeAllViews();
        }
        else {
            Toast.makeText(this, getString(R.string.navigation_failure), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Checks for validity before pushing interaction views on top of the stack
     * @return true if valid, false otherwise
     */
    private boolean checkValidity() {
        if (stackedCups == null) {
            Toast.makeText(this, getString(R.string.stackcup_null_message), Toast.LENGTH_LONG).show();

            return false;
        }

        return true;
    }
}
