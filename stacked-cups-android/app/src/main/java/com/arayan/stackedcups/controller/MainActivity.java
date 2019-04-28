package com.arayan.stackedcups.controller;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.arayan.stackedcups.R;
import com.arayan.stackedcups.controller.interfaces.ViewController;
import com.arayan.stackedcups.controller.views.PageAbout;
import com.arayan.stackedcups.controller.views.PageFillCup;
import com.arayan.stackedcups.controller.views.PageQuery;
import com.arayan.stackedcups.model.CupStackLUT;
import com.arayan.stackedcups.model.WhiskeyCup;
import com.arayan.stackedcups.model.exceptions.InvalidArgumentException;
import com.arayan.stackedcups.model.interfaces.CupLUT;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ViewGroup holderView;
    private CupLUT stackedCups;

    // our page views, inflated during onCreate
    private ViewController pageAbout;
    private ViewController pageFillCup;
    private ViewController pageQuery;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_fill_cups:
                        pageFillCup.show(holderView);
                        return true;
                    case R.id.navigation_query:
                        pageQuery.show(holderView);
                        return true;
                    case R.id.navigation_about:
                        pageAbout.show(holderView);
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

        try {
            stackedCups = new CupStackLUT((row, col) -> new WhiskeyCup(250, row, col));

            createPages();

            pageFillCup.show(holderView);
        }
        catch (final InvalidArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Inflate all our navigation pages, we will push/pop these pages to/from
     * the stack
     */
    private void createPages() {
        pageAbout = new PageAbout(stackedCups, this);
        pageFillCup = new PageFillCup(stackedCups, this);
        pageQuery = new PageQuery(stackedCups, this);
    }
}
