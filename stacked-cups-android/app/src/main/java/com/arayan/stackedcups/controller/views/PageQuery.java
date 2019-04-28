package com.arayan.stackedcups.controller.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.arayan.stackedcups.R;
import com.arayan.stackedcups.model.interfaces.CupLUT;

public final class PageQuery extends BaseView {

    private final TextView currentLiquid;
    private final TextView liquidRowAddView;
    private final TextView liquidColAddView;

    public PageQuery(@NonNull final CupLUT lut, @NonNull final Activity mainActivity) {
        super(lut, R.layout.page_query, mainActivity);

        currentLiquid = getView().findViewById(R.id.query_current);
        liquidRowAddView = getView().findViewById(R.id.query_row);
        liquidColAddView = getView().findViewById(R.id.query_col);
        final Button showLiquidButton = getView().findViewById(R.id.query_show_button);

        // on button click, we check the volume that we need to add and add it in
        showLiquidButton.setOnClickListener(view -> {

        });
    }

    @Override
    public void show(@NonNull final ViewGroup parent) {
        super.show(parent);

        updateVolumeDisplay("volume at cup(0,0) is " + getLut().getRootCup().getCurrentVolume() + "ml");
    }

    private void updateVolumeDisplay(final String text) {
        currentLiquid.setText(text);
    }
}
