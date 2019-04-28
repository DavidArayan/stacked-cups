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

public final class PageFillCup extends BaseView {

    private final TextView currentLiquid;
    private final TextView liquidAddView;

    public PageFillCup(@NonNull final CupLUT lut, @NonNull final Activity mainActivity) {
        super(lut, R.layout.page_fillcup, mainActivity);

        currentLiquid = getView().findViewById(R.id.fillcups_current);
        liquidAddView = getView().findViewById(R.id.fillcups_volume_input);
        final Button addLiquidButton = getView().findViewById(R.id.fillcup_add_liquid_button);

        // on button click, we check the volume that we need to add and add it in
        addLiquidButton.setOnClickListener(view -> {
            final int volumeToAdd = Integer.valueOf(liquidAddView.getText().toString());

            if (volumeToAdd > 0) {
                try {
                    getLut().fill(volumeToAdd);
                }
                catch (final Exception e) {
                    Toast.makeText(getView().getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getView().getContext(), "Input volume must be greater than zero", Toast.LENGTH_SHORT).show();
            }

            updateVolumeDisplay();
        });
    }

    @Override
    public void show(@NonNull final ViewGroup parent) {
        super.show(parent);

        updateVolumeDisplay();
    }

    @SuppressLint("SetTextI18n")
    private void updateVolumeDisplay() {
        currentLiquid.setText("Total Liquid is " + getLut().getTotalVolume() + "ml");
    }
}
