package com.arayan.stackedcups;

import com.arayan.stackedcups.model.IntegerCoordinate;
import com.arayan.stackedcups.model.exceptions.InvalidArgumentException;
import com.arayan.stackedcups.model.interfaces.Coordinate;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public final class IntegerCoordinateTests {

    @Test
    public void createCoordinateDefault() {
        final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate();

        assertEquals("expected x-value to be 65535", 65535, (int)coordinate.getX());
        assertEquals("expected y-value to be 65535", 65535, (int)coordinate.getY());
    }

    @Test
    public void createCoordinateZeroXY() throws InvalidArgumentException {
        final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate(0,0);

        assertEquals("expected x-value to be 0", 0, (int)coordinate.getX());
        assertEquals("expected y-value to be 0", 0, (int)coordinate.getY());
    }

    @Test
    public void createCoordinateX() throws InvalidArgumentException {
        final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate(782,0);

        assertEquals("expected x-value to be 782", 782, (int)coordinate.getX());
        assertEquals("expected y-value to be 0", 0, (int)coordinate.getY());
    }

    @Test
    public void createCoordinateY() throws InvalidArgumentException {
        final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate(0,1094);

        assertEquals("expected x-value to be 0", 0, (int)coordinate.getX());
        assertEquals("expected y-value to be 1094", 1094, (int)coordinate.getY());
    }

    @Test
    public void createCoordinateXY() throws InvalidArgumentException {
        final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate(893,782);

        assertEquals("expected x-value to be 893", 893, (int)coordinate.getX());
        assertEquals("expected y-value to be 782", 782, (int)coordinate.getY());
    }

    @Test
    public void createCoordinateLooped() throws InvalidArgumentException {
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                final int xVal = x * 6;
                final int yVal = y * 9;
                final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate(xVal, yVal);

                assertEquals("expected x-value to be " + xVal, xVal, (int)coordinate.getX());
                assertEquals("expected y-value to be " + yVal, yVal, (int)coordinate.getY());
            }
        }
    }

    @Test
    public void createCoordinateXMax() throws InvalidArgumentException {
        final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate(65535,0);

        assertEquals("expected x-value to be 65535", 65535, (int)coordinate.getX());
        assertEquals("expected y-value to be 0", 0, (int)coordinate.getY());
    }

    @Test
    public void createCoordinateYMax() throws InvalidArgumentException {
        final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate(0,65535);

        assertEquals("expected x-value to be 0", 0, (int)coordinate.getX());
        assertEquals("expected y-value to be 65535", 65535, (int)coordinate.getY());
    }

    @Test
    public void createCoordinateXYMax() throws InvalidArgumentException {
        final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate(65535,65535);

        assertEquals("expected x-value to be 65535", 65535, (int)coordinate.getX());
        assertEquals("expected y-value to be 65535", 65535, (int)coordinate.getY());
    }

    @Test
    public void createCoordinateXMaxOverflow() throws InvalidArgumentException {
        final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate(Integer.MAX_VALUE,0);

        assertEquals("expected x-value to be 65535", 65535, (int)coordinate.getX());
        assertEquals("expected y-value to be 0", 0, (int)coordinate.getY());
    }

    @Test
    public void createCoordinateYMaxOverflow() throws InvalidArgumentException {
        final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate(0, Integer.MAX_VALUE);

        assertEquals("expected x-value to be 0", 0, (int)coordinate.getX());
        assertEquals("expected y-value to be 65535", 65535, (int)coordinate.getY());
    }

    @Test
    public void createCoordinateXYMaxOverflow() throws InvalidArgumentException {
        final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate(Integer.MAX_VALUE, Integer.MAX_VALUE);

        assertEquals("expected x-value to be 65535", 65535, (int)coordinate.getX());
        assertEquals("expected y-value to be 65535", 65535, (int)coordinate.getY());
    }

    @Test
    public void copyCoordinates() throws InvalidArgumentException {
        for (int x = 1; x < 100; x++) {
            final int xVal = x * 2;
            final int yVal = x * 7;

            final Coordinate<Integer, Integer, Integer> coordinate1 = new IntegerCoordinate(xVal, yVal);
            final Coordinate<Integer, Integer, Integer> coordinate2 = new IntegerCoordinate(xVal, yVal);

            final Coordinate<Integer, Integer, Integer> coordinate3 = new IntegerCoordinate(yVal, xVal);

            assertEquals("expected values to be equal " + coordinate1.getIndex() + " " + coordinate2.getIndex(), coordinate1, coordinate2);
            assertNotEquals("expected values not to be equal " + coordinate1.getIndex() + " " + coordinate3.getIndex(), coordinate1, coordinate3);
            assertNotEquals("expected values not to be equal " + coordinate2.getIndex() + " " + coordinate3.getIndex(), coordinate2, coordinate3);
        }
    }

    @Test
    public void hashCoordinates() throws InvalidArgumentException {
        final Map<Coordinate, Integer> map = new HashMap<>();

        // insert into the hashmap
        for (int x = 0; x < 100; x++) {
            final int xVal = x * 2;
            final int yVal = x * 7;

            final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate(xVal, yVal);

            map.put(coordinate, coordinate.getIndex());
        }

        // read from the hashmap
        for (int x = 0; x < 100; x++) {
            final int xVal = x * 2;
            final int yVal = x * 7;

            final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate(xVal, yVal);

            assertTrue("expected values to be in hashmap " + coordinate.getIndex(), map.containsKey(coordinate));
            assertEquals("expected values to match hashed value", map.get(coordinate), coordinate.getIndex());
        }
    }

    @Test
    public void createCoordinateXNegative() {
        try {
            new IntegerCoordinate(-1, 0);

            fail("expected an exception to be thrown as negative x values are not allowed");
        }
        catch (final InvalidArgumentException ignored) {}
    }

    @Test
    public void createCoordinateYNegative() {
        try {
            new IntegerCoordinate(0, -1);

            fail("expected an exception to be thrown as negative y values are not allowed");
        }
        catch (final InvalidArgumentException ignored) {}
    }

    @Test
    public void createCoordinateXYNegative() {
        try {
            new IntegerCoordinate(-1, -1);

            fail("expected an exception to be thrown as negative xy values are not allowed");
        }
        catch (final InvalidArgumentException ignored) {}
    }
}
