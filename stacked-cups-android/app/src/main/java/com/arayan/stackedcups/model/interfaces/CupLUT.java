package com.arayan.stackedcups.model.interfaces;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import com.arayan.stackedcups.model.exceptions.InvalidArgumentException;

import java.io.InvalidObjectException;

/**
 * Provides structures and methods for interfacing
 * with stacked Cups in a hierarchical manner
 */
public interface CupLUT {

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
    @NonNull Cup getCup(final int rowIndex, final int colIndex);

    /**
     * @return  Returns the Cup at the root position (0,0). This method
     *          will always return a valid and Non-Null Cup object.
     */
    @NonNull Cup getRootCup();

    /**
     * Function splits the provided Cup into it's left and right sub-children
     * and stores them internally. Use the getCup() functionality to query
     * for the Cups.
     *
     * @param cup The Cup to split. Must be valid and Non-Null, also must already
     *            exist in the LUT structure. Use getRootCup() to split the root Cup
     *            initially.
     *
     * @return  Returns a Pair of Cups representing the left and right splits. This function
     *          will return a Pair of Cups that are invalid - check via Cup.isValid() if
     *          the split fails
     */
    @NonNull Pair<Cup, Cup> splitAndStoreChildren(@NonNull final Cup cup) throws InvalidArgumentException, InvalidObjectException;

    /**
     * @return All the volume in all the cups summed
     */
    int getTotalVolume();

    /**
     * Fills the stack of cups with volume starting from the top of the stack
     * and filling downwards.
     *
     * @param volume The amount of volume to fill
     */
    void fill(final int volume) throws InvalidObjectException, InvalidArgumentException;
}
