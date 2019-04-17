package com.arayan.stackedcups.model.interfaces;

import androidx.annotation.NonNull;

/**
 * Provides structures and methods for interfacing
 * with stacked Cups in a hierarchical manner
 */
public interface CupLUT<T extends Cup> {

    /**
     * Provided a row and column (2D coordinates) returns a Cup
     * from the provided Coordinate.
     *
     * @param rowIndex row or y-coordinate of the Cup, must be >= 0
     * @param colIndex col or x-coordinate of the Cup, must be >= 0
     *
     * @return  Returns the Cup at the provided coordinate. Note that
     *          this method uses the Non-Null pattern hence the returned
     *          object will never be null. Check via Cup.isValid() to ensure
     *          that a valid Cup is returned.
     */
    @NonNull T getCup(final int rowIndex, final int colIndex);
}
