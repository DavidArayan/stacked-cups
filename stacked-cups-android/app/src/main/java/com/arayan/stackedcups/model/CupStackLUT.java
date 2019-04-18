package com.arayan.stackedcups.model;

import com.arayan.stackedcups.model.exceptions.InvalidArgumentException;
import com.arayan.stackedcups.model.interfaces.Coordinate;
import com.arayan.stackedcups.model.interfaces.Cup;
import com.arayan.stackedcups.model.interfaces.CupLUT;

import java.io.InvalidObjectException;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

/**
 * Primary LUT structure for dealing with a stack of Cups. Internally this uses
 * an expandable array for storing the Cups using Coordinates.
 *
 * For function signature documentation, refer to the interfaces
 */
public final class CupStackLUT implements CupLUT {

    /**
     * Represents a simple factory implementation to create Cups by
     * the implementing function
     */
    public interface CupStackLUTFactory {
        Cup create(final int row, final int col) throws InvalidArgumentException;
    }

    // stores all our Cups using the Coordinate object as a hash key
    // this is because each Cup has a specific coordinate that cannot
    // be duplicated, ie, cannot have two cups at position (0,0)
    // for a 3D stacked cups, consider adding a z coordinate to keep the same
    // level of logic.
    private final Map<Coordinate<Integer, Integer, Integer>, Cup> cupsLUT = new HashMap<>();

    // this will never be null
    private final CupStackLUTFactory factory;

    public CupStackLUT(final CupStackLUTFactory factory) throws InvalidArgumentException {
        if (factory == null) {
            throw new InvalidArgumentException("factory", "the provided argument cannot be null, factory is required to construct new objects on demand");
        }

        this.factory = factory;

        // generate our root cup at position 0,0
        createRootCup(this.factory);
    }

    /**
     * Generate the root cup at position 0,0, called
     * by the constructor
     */
    private void createRootCup(final CupStackLUTFactory factory) throws InvalidArgumentException {
        final Cup newCup = factory.create(0, 0);

        if (newCup != null) {
            // insert into our hash table according to the coordinate
            if (newCup.isValid()) {
                cupsLUT.put(newCup.getCoordinate(), newCup);

                return;
            }

            // we refuse to proceed if the root object was somehow an invalid object
            // perhaps tricked by the factory?
            throw new InvalidArgumentException("factory.create()", "the generated variable from the factory must be a valid object, check Cup.isValid()");
        }

        // we refuse to proceed if the root cannot be generated for whatever reason
        throw new InvalidArgumentException("factory.create()", "the generated variable from the factory cannot be null");
    }

    @Override
    public @NonNull Cup getRootCup() {
        return getCup(0,0);
    }

    @Override
    public @NonNull Pair<Cup, Cup> splitAndStoreChildren(@NonNull final Cup cup) throws InvalidArgumentException, InvalidObjectException {
        final Coordinate<Integer, Integer, Integer> leftCoord = cup.getLeftChildCoordinate();
        final Coordinate<Integer, Integer, Integer> rightCoord = cup.getRightChildCoordinate();

        // either grab or create the new cups
        final Cup lCup = cupsLUT.containsKey(leftCoord) ? cupsLUT.get(leftCoord) : factory.create(leftCoord.getX(), leftCoord.getY());
        final Cup rCup = cupsLUT.containsKey(rightCoord) ? cupsLUT.get(rightCoord) : factory.create(rightCoord.getX(), rightCoord.getY());

        // we don't accept null cups
        if (lCup == null) {
            throw new InvalidObjectException("the provided left-cup cannot be null");
        }

        // we only accept valid cups
        if (!lCup.isValid()) {
            throw new InvalidObjectException("the provided left-cup must be valid");
        }

        // all cups must be the same capacity as the root cup
        if (lCup.getCapacity() != getRootCup().getCapacity()) {
            throw new InvalidObjectException("the provided left-cup must have the same capacity as all other cups");
        }

        // we don't accept null cups
        if (rCup == null) {
            throw new InvalidObjectException("the provided right-cup cannot be null");
        }

        // we only accept valid cups
        if (!rCup.isValid()) {
            throw new InvalidObjectException("the provided right-cup must be valid");
        }

        // all cups must be the same capacity as the root cup
        if (rCup.getCapacity() != getRootCup().getCapacity()) {
            throw new InvalidObjectException("the provided right-cup must have the same capacity as all other cups");
        }

        cupsLUT.put(lCup.getCoordinate(), lCup);
        cupsLUT.put(rCup.getCoordinate(), rCup);

        // finally return our split cups
        return new Pair<>(lCup, rCup);
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

        // safe to send as UnfillableCup instance never stores state
        return UnfillableCup.INSTANCE;
    }

    @Override
    public int getTotalVolume() {
        // start from the root and propagate down
        return getTotalVolumeFrom(getRootCup());
    }

    /**
     * Private functionality to recursively grab all the liquid
     * volume from all of the cups.
     *
     * This is essentially a psuedo-BST type recursion to all children nodes.
     * If the overall construction logic is correct, this should never get
     * stuck.. however it might cause a stack overflow depending on the depth
     */
    private int getTotalVolumeFrom(@NonNull final Cup cup) {
        int totalVolume = cup.getCurrentVolume();

        final Cup leftCup = getCup(cup.getLeftChildCoordinate().getX(), cup.getLeftChildCoordinate().getY());

        if (leftCup.isValid()) {
            totalVolume += getTotalVolumeFrom(leftCup);
        }

        final Cup rightCup = getCup(cup.getRightChildCoordinate().getX(), cup.getRightChildCoordinate().getY());

        if (rightCup.isValid()) {
            totalVolume += getTotalVolumeFrom(rightCup);
        }

        return totalVolume;
    }
}
