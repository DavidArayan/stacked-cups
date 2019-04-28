package com.arayan.stackedcups.controller.interfaces;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

/**
 * Simple Controller interface for the various views
 * in our app
 */
public interface ViewController {
    void show(@NonNull final ViewGroup parent);
}
