package com.arayan.stackedcups.model;

import com.arayan.stackedcups.model.exceptions.InvalidArgumentException;
import com.arayan.stackedcups.model.interfaces.Coordinate;
import com.arayan.stackedcups.model.interfaces.Cup;
import com.arayan.stackedcups.model.interfaces.CupLUT;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

/**
 * Primary LUT structure for dealing with a stack of Cups. Internally this uses
 * an expandable array for storing the Cups using Coordinates.
 */
public final class CupStackLUT implements CupLUT {

    /**
     * Represents a simple factory implementation to create Cups by
     * the implementing function
     */
    public interface CupStackLUTFactory {
        Cup create(final int row, final int col) throws InvalidArgumentException;
    }

    private final Map<Coordinate<Integer, Integer, Integer>, Cup> cupsLUT = new HashMap<>();
    private final CupStackLUTFactory factory;

    public CupStackLUT(final CupStackLUTFactory factory) throws InvalidArgumentException {
        if (factory == null) {
            throw new InvalidArgumentException("factory", "the provided argument cannot be null, factory is required to construct new objects on demand");
        }

        this.factory = factory;
    }

    @Override
    public @NonNull Cup getCup(final int rowIndex, final int colIndex) {
        final Coordinate<Integer, Integer, Integer> lutCoordinate;

        try {
            lutCoordinate = new IntegerCoordinate(rowIndex, colIndex);
        }
        catch (final InvalidArgumentException e) {
            return UnfillableCup.INSTANCE;
        }

        // if the requested cup exists, just return as normal
        final Cup retCup = cupsLUT.get(lutCoordinate);

        if (retCup != null) {
            return retCup;
        }

        // try and construct a new cup at requested coordinate
        try {
            final Cup newCup = factory.create(rowIndex, colIndex);

            if (newCup != null) {
                // insert into our hash table according to the coordinate
                cupsLUT.put(newCup.getCoordinate(), newCup);
            }
        }
        catch (final InvalidArgumentException e) {
            // empty catch clause. If this occurs then the Cup being constructed
            // was not using a valid coordinate, as such it cannot be created
            // we simply return a NULL_CUP to maintain our NON_NULL pattern
        }

        // safe to send as UnfillableCup instance never stores state
        return UnfillableCup.INSTANCE;
    }
}
