package com.arayan.stackedcups.controller.views;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.arayan.stackedcups.R;
import com.arayan.stackedcups.model.interfaces.CupLUT;

public final class PageFillCup extends BaseView {

    public PageFillCup(@NonNull final CupLUT lut, @NonNull final Activity mainActivity) {
        super(lut, R.layout.page_fillcup, mainActivity);
    }
}