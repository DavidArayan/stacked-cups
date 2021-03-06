package com.arayan.stackedcups.model;

import com.arayan.stackedcups.model.exceptions.InvalidCallException;
import com.arayan.stackedcups.model.interfaces.Coordinate;
import com.arayan.stackedcups.model.interfaces.Cup;
import com.arayan.stackedcups.model.interfaces.CupLUT;

import androidx.annotation.NonNull;

/**
 * This particular Cup instance is used to implement a Non-Null design pattern
 * for CupLUT structure. Spatial queries to grab Cups that fall outside the standard ranges
 * will return this object's instance, which essentially does nothing but conforms
 * to the Cup interface.
 */
public final class UnfillableCup implements Cup {

    // the only instance that should exist for this class
    private static final Cup INSTANCE;

    static {
        INSTANCE = new UnfillableCup();
    }

    private static final Coordinate<Integer, Integer, Integer> COORDINATE = new IntegerCoordinate();

    // don't allow an instance of this class to be created anywhere
    private UnfillableCup() {}

    public static final Cup getInstance() {
        return INSTANCE;
    }

    @Override
    public int getCurrentVolume() {
        return 0;
    }

    @Override
    public int getCapacity() {
        return 0;
    }

    @Override
    public int drainCurrentVolume() {
        return 0;
    }

    @Override
    public void fill(@NonNull final CupLUT lut, final int volume) {
        // throw a runtime exception letting the user know something went wrong
        throw new InvalidCallException("UnfillableCup -> fill(CupLUT, Integer) -> called a function with no implementation, did you forget to call Cup.isValid()?");
    }

    @Override
    public @NonNull Coordinate<Integer, Integer, Integer> getCoordinate() {
        return COORDINATE;
    }

    @Override
    public @NonNull Coordinate<Integer, Integer, Integer> getLeftChildCoordinate() {
        return getCoordinate();
    }

    @Override
    public @NonNull Coordinate<Integer, Integer, Integer> getRightChildCoordinate() {
        return getCoordinate();
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public int hashCode() {
        return getCoordinate().getIndex();
    }
}
