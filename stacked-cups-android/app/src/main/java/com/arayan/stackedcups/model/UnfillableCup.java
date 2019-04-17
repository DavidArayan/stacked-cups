package com.arayan.stackedcups.model;

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
    public static final UnfillableCup INSTANCE;

    static {
        INSTANCE = new UnfillableCup();
    }

    // represents a coordinate that will never fall within a valid range
    // when hashing against other stacked cups
    private static final Coordinate<Integer, Integer, Integer> COORDINATE = new IntegerCoordinate(0, Integer.MAX_VALUE);

    // don't allow an instance of this class to be created anywhere
    private UnfillableCup() {}

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
        throw new RuntimeException("UnfillableCup -> fill(CupLUT, Integer) -> called a function with no implementation, did you forget to call Cup.isValid()?");
    }

    @Override
    public @NonNull Coordinate<Integer, Integer, Integer> getCoordinate() {
        return COORDINATE;
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
        return COORDINATE.getIndex();
    }
}
