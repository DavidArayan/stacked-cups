package com.arayan.stackedcups.model.interfaces;

import com.arayan.stackedcups.model.exceptions.InvalidArgumentException;

import java.io.InvalidObjectException;

import androidx.annotation.NonNull;

/**
 * Interface functionality for a cup carrying a dynamic amount
 * of volume in ml (milliliters).
 *
 * Since Java does not support unsigned integer types, the effective
 * maximum carrying capacity for each cup would be 2^31 ml
 */
public interface Cup {

    /**
     * @return  Returns the current volume of liquid in the cup
     *          measured in ml (milliliters)
     */
    int getCurrentVolume();

    /**
     * @return  Returns the current maximum volume capacity of
     *          the cup in ml (milliliters)
     */
    int getCapacity();

    /**
     * @return  Drains all liquid from the current cup and returns
     *          the amount of liquid drained in ml (milliliters).
     */
    int drainCurrentVolume();

    /**
     * Fills the current cup with a volume of liquid up to a maximum carrying
     * capacity. If capacity is reached, the liquid will propagate dynamically
     * to children nodes, halved for each child equally.
     *
     * @param lut The LUT table responsible for storing all Cups in a hierarchy, cannot be null.
     * @param volume The amount of volume to fill, must be >= 0
     */
    void fill(@NonNull final CupLUT lut, int volume) throws InvalidArgumentException, InvalidObjectException;

    /**
     * @return  Generate a hashing index to be used for LUT operations,
     *          this effectively replaces the hashCode() functionality.
     */
    @NonNull Coordinate<Integer, Integer, Integer> getCoordinate();

    /**
     * @return  Generate a hashing index to be used for LUT operations,
     *          this Coordinate can be used to grab the Cup that is the
     *          left child of this Cup.
     */
    @NonNull Coordinate<Integer, Integer, Integer> getLeftChildCoordinate();

    /**
     * @return  Generate a hashing index to be used for LUT operations,
     *          this Coordinate can be used to grab the Cup that is the
     *          right child of this Cup.
     */
    @NonNull Coordinate<Integer, Integer, Integer> getRightChildCoordinate();

    /**
     * @return  Returns true if this cup is a valid cup. Sometimes cups
     *          can be generated that are not part of the stacked cups
     *          hierarchy.
     *          This check is used in the Non-Null Pattern.
     */
    boolean isValid();

    /**
     * @return  Returns if the current cup is not holding any liquid
     */
    boolean isEmpty();
}
