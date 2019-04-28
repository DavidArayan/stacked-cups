package com.arayan.stackedcups.controller.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.arayan.stackedcups.controller.interfaces.ViewController;
import com.arayan.stackedcups.model.interfaces.CupLUT;

/**
 * Handles all the base functionality for all views
 */
public abstract class BaseView implements ViewController {

    private final CupLUT lut;
    private final View view;

    @SuppressLint("InflateParams")
    BaseView(@NonNull final CupLUT lut, final int viewID, @NonNull final Activity mainActivity) {
        this.lut = lut;
        this.view = mainActivity.getLayoutInflater().inflate(viewID, null);
    }

    @Override
    public void show(@NonNull final ViewGroup parent) {
        if (lut != null) {
            parent.removeAllViews();

            parent.addView(view);
        }
    }

    protected CupLUT getLut() {
        return lut;
    }

    protected View getView() {
        return view;
    }
}
