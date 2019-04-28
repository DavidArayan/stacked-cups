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

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_fill_cups:
                        showView(pageFillCup);
                        return true;
                    case R.id.navigation_query:
                        showView(pageQuery);
                        return true;
                    case R.id.navigation_about:
                        showView(pageAbout);
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        holderView = findViewById(R.id.holder_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        try {
            // it would be nice to have a UI to customize the volumeCapacity being entered..
            stackedCups = new CupStackLUT((row, col) -> new WhiskeyCup(250, row, col));

            createPages();

            showView(pageFillCup);
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

    /**
     * Perform some simple null checks and place the provided view
     * into the view hierarchy
     */
    private void showView(final ViewController controller) {
        if (holderView != null && controller != null) {
            controller.show(holderView);
        }
    }
}
