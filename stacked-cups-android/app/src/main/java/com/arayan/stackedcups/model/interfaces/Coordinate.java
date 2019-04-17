package com.arayan.stackedcups.model.interfaces;

import com.arayan.stackedcups.model.exceptions.InvalidArgumentException;

import androidx.annotation.NonNull;

/**
 * Represents a simple 2D coordinate object.
 * Special functionality of the Coordinate objects are that
 * each Coordinate object is hashed according to it's internal values.
 */
public interface Coordinate<X, Y, HashType> {
    X getX();
    Y getY();
    HashType getIndex();

    @NonNull Coordinate<X, Y, HashType> copy() throws InvalidArgumentException;
}
