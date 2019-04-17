package com.arayan.stackedcups.model;

import com.arayan.stackedcups.model.exceptions.InvalidArgumentException;
import com.arayan.stackedcups.model.interfaces.Coordinate;

import androidx.annotation.NonNull;

/**
 * IntegerCoordinate stores a 2D Integer coordinate system
 * with a maximum of 16 bits or 2^16 per component for a total of
 * 2^32 coordinates.
 *
 * Minimum value for X or Y components = 0
 * Maximum value for X or Y components = 65535
 *
 * Values outside the supported range will be masked
 */
public final class IntegerCoordinate implements Coordinate<Integer, Integer, Integer> {

    // we only store 2^16 or [0-65535] values per coordinate component
    // use this to mask the input values into their proper range
    private static final int BIT_MASK_16 = 0x0000FFFF;

    // an immutable coordinate value, this also acts as a special hash
    // value for our coordinate
    private final int coordinate;

    public IntegerCoordinate() {
        // combine the first 16 bits of each coordinate into a single coordinate
        this.coordinate = (Integer.MAX_VALUE & BIT_MASK_16) << 16 | (Integer.MAX_VALUE & BIT_MASK_16);
    }

    public IntegerCoordinate(final int row, final int col) throws InvalidArgumentException {
        if (row < 0) {
            throw new InvalidArgumentException("row", "the provided argument must be >= 0");
        }

        if (col < 0) {
            throw new InvalidArgumentException("col", "the provided argument must be >= 0");
        }

        // combine the first 16 bits of each coordinate into a single coordinate
        this.coordinate = (row & BIT_MASK_16) << 16 | (col & BIT_MASK_16);
    }

    @Override
    public Integer getX() {
        return (coordinate >> 16) & BIT_MASK_16;
    }

    @Override
    public Integer getY() {
        return coordinate & BIT_MASK_16;
    }

    @Override
    public Integer getIndex() {
        return coordinate;
    }

    @Override
    public @NonNull Coordinate<Integer, Integer, Integer> copy() throws InvalidArgumentException {
        return new IntegerCoordinate(getX(), getY());
    }

    @Override
    public int hashCode() {
        return getIndex();
    }
}
