package com.arayan.stackedcups.model.interfaces;

import com.arayan.stackedcups.model.exceptions.InvalidArgumentException;

import androidx.annotation.NonNull;

/**
 * Represents a simple, immutable 2D coordinate object.
 * Special functionality of the Coordinate objects are that
 * each Coordinate object is hashed according to it's internal values.
 */
public interface Coordinate<X, Y, H> {
    X getX();
    Y getY();
    H getIndex();

    @NonNull Coordinate<X, Y, H> copy() throws InvalidArgumentException;
}
