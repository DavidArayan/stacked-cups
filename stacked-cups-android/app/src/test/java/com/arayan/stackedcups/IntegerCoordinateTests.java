package com.arayan.stackedcups;

import com.arayan.stackedcups.model.IntegerCoordinate;
import com.arayan.stackedcups.model.exceptions.InvalidArgumentException;
import com.arayan.stackedcups.model.interfaces.Coordinate;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntegerCoordinateTests {

    @Test
    public void create_coordinate_default() {
        final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate();

        assertEquals("expected x-value to be 65535", 65535, (int)coordinate.getX());
        assertEquals("expected y-value to be 65535", 65535, (int)coordinate.getY());
    }

    @Test
    public void create_coordinate_zero_xy() throws InvalidArgumentException {
        final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate(0,0);

        assertEquals("expected x-value to be 0", 0, (int)coordinate.getX());
        assertEquals("expected y-value to be 0", 0, (int)coordinate.getY());
    }

    @Test
    public void create_coordinate_x() throws InvalidArgumentException {
        final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate(782,0);

        assertEquals("expected x-value to be 782", 782, (int)coordinate.getX());
        assertEquals("expected y-value to be 0", 0, (int)coordinate.getY());
    }

    @Test
    public void create_coordinate_y() throws InvalidArgumentException {
        final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate(0,1094);

        assertEquals("expected x-value to be 0", 0, (int)coordinate.getX());
        assertEquals("expected y-value to be 1094", 1094, (int)coordinate.getY());
    }

    @Test
    public void create_coordinate_xy() throws InvalidArgumentException {
        final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate(893,782);

        assertEquals("expected x-value to be 893", 893, (int)coordinate.getX());
        assertEquals("expected y-value to be 782", 782, (int)coordinate.getY());
    }

    @Test
    public void create_coordinate_looped() throws InvalidArgumentException {
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
    public void create_coordinate_x_max() throws InvalidArgumentException {
        final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate(65535,0);

        assertEquals("expected x-value to be 65535", 65535, (int)coordinate.getX());
        assertEquals("expected y-value to be 0", 0, (int)coordinate.getY());
    }

    @Test
    public void create_coordinate_y_max() throws InvalidArgumentException {
        final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate(0,65535);

        assertEquals("expected x-value to be 0", 0, (int)coordinate.getX());
        assertEquals("expected y-value to be 65535", 65535, (int)coordinate.getY());
    }

    @Test
    public void create_coordinate_xy_max() throws InvalidArgumentException {
        final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate(65535,65535);

        assertEquals("expected x-value to be 65535", 65535, (int)coordinate.getX());
        assertEquals("expected y-value to be 65535", 65535, (int)coordinate.getY());
    }

    @Test
    public void create_coordinate_x_max_overflow() throws InvalidArgumentException {
        final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate(Integer.MAX_VALUE,0);

        assertEquals("expected x-value to be 65535", 65535, (int)coordinate.getX());
        assertEquals("expected y-value to be 0", 0, (int)coordinate.getY());
    }

    @Test
    public void create_coordinate_y_max_overflow() throws InvalidArgumentException {
        final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate(0, Integer.MAX_VALUE);

        assertEquals("expected x-value to be 0", 0, (int)coordinate.getX());
        assertEquals("expected y-value to be 65535", 65535, (int)coordinate.getY());
    }

    @Test
    public void create_coordinate_xy_max_overflow() throws InvalidArgumentException {
        final Coordinate<Integer, Integer, Integer> coordinate = new IntegerCoordinate(Integer.MAX_VALUE, Integer.MAX_VALUE);

        assertEquals("expected x-value to be 65535", 65535, (int)coordinate.getX());
        assertEquals("expected y-value to be 65535", 65535, (int)coordinate.getY());
    }
}
