package com.arayan.stackedcups.model;

import com.arayan.stackedcups.model.exceptions.InvalidArgumentException;
import com.arayan.stackedcups.model.interfaces.Coordinate;
import com.arayan.stackedcups.model.interfaces.Cup;
import com.arayan.stackedcups.model.interfaces.CupLUT;

import java.io.InvalidObjectException;

import androidx.annotation.NonNull;

/**
 * Represents a Cup that can store only good quality Whiskey liquid.
 * As per the rules, I'm restricting the pouring of anything other than
 * good quality whiskey into this Cup.
 */
public final class WhiskeyCup implements Cup {

    // our maximum capacity, technically the cups being stacked
    // don't have to all have the same volume capacity
    private final int volumeCapacity;

    // each Cup has a special unique coordinate
    private final Coordinate<Integer, Integer, Integer> coordinate;

    // the amount of whiskey we are holding
    private int currentVolume;

    public WhiskeyCup(final int volumeCapacity, final int row, final int col) throws InvalidArgumentException {
        if (row < 0) {
            // throw error
            throw new InvalidArgumentException("row", "the provided argument cannot be less than 0");
        }

        // the way Cups are stacked, column can never be larger than the row
        if (col > row) {
            // throw error
            throw new InvalidArgumentException("col", "the provided argument cannot be greater than the row");
        }

        if (col < 0) {
            // throw error
            throw new InvalidArgumentException("col", "the provided argument cannot be less than 0");
        }

        if (volumeCapacity <= 0) {
            // throw error
            throw new InvalidArgumentException("volumeCapacity", "the provided argument must be greater than 0");
        }

        this.coordinate = new IntegerCoordinate(row, col);

        this.volumeCapacity = volumeCapacity;
        this.currentVolume = 0;
    }

    @Override
    public int getCurrentVolume() {
        return currentVolume;
    }

    @Override
    public int getCapacity() {
        return volumeCapacity;
    }

    @Override
    public int drainCurrentVolume() {
        final int cVolume = currentVolume;

        currentVolume = 0;

        return cVolume;
    }

    @Override
    public void fill(@NonNull final CupLUT lut, final int volume) throws InvalidArgumentException, InvalidObjectException {
        // volume cannot be negative
        if (volume < 0) {
            // throw error
            throw new InvalidArgumentException("volume", "the provided argument cannot be less than 0");
        }

        final int newVolume = getCurrentVolume() + volume;
        final int difference = getCapacity() - newVolume;

        // nothing overflows to child Cups, simply update
        // and exit
        if (difference <= 0) {
            currentVolume = newVolume;

            return;
        }

        // we have an overflow to child cups, set this node's volume
        // to capacity and recurse down
        currentVolume = getCapacity();

        // make sure nothing goes to waste, this gives an approximate
        // equal distribution between the cups
        final int leftVolume = difference / 2;
        final int rightVolume = difference - leftVolume;

        // get our current Cup coordinates so we can lookup our child Cups
        final int i = getCoordinate().getX();
        final int j = getCoordinate().getY();

        final Cup leftCup = lut.getCup(i + 1, j);

        // this is a safety check, but it should never happen
        if (!leftCup.isValid()) {
            // throw error
            throw new InvalidObjectException("the left child node from CupLUT.getCup(" + (i + 1) + "," + j + ") is invalid");
        }

        final Cup rightCup = lut.getCup(i + 1, j + 1);

        // this is a safety check, but it should never happen
        if (!rightCup.isValid()) {
            // throw error
            throw new InvalidObjectException("the right child node from CupLUT.getCup(" + (i + 1) + "," + (j + 1) + ") is invalid");
        }

        // these values will propagate down to child nodes
        leftCup.fill(lut, leftVolume);
        rightCup.fill(lut, rightVolume);
    }

    @Override
    public @NonNull Coordinate<Integer, Integer, Integer> getCoordinate() {
        return coordinate;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return getCurrentVolume() == 0;
    }

    @Override
    public int hashCode() {
        return coordinate.getIndex();
    }
}
