package com.arayan.stackedcups.controller.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.arayan.stackedcups.R;
import com.arayan.stackedcups.model.interfaces.CupLUT;

public final class PageFillCup extends BaseView {

    private final TextView currentLiquid;

    public PageFillCup(@NonNull final CupLUT lut, @NonNull final Activity mainActivity) {
        super(lut, R.layout.page_fillcup, mainActivity);

        currentLiquid = getView().findViewById(R.id.fillcups_current);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void show(@NonNull final ViewGroup parent) {
        super.show(parent);

        currentLiquid.setText("Total Liquid is " + getLut().getTotalVolume() + "ml");
    }
}
