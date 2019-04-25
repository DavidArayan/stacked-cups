package com.arayan.stackedcups;

import com.arayan.stackedcups.model.CupStackLUT;
import com.arayan.stackedcups.model.WhiskeyCup;
import com.arayan.stackedcups.model.exceptions.InvalidArgumentException;
import com.arayan.stackedcups.model.interfaces.CupLUT;

import org.junit.Test;

import java.io.InvalidObjectException;
import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public final class CupTests {

    private static final int CUP_VOLUME = 250;

    @Test
    public void cupFillInvalid() throws InvalidArgumentException, InvalidObjectException {
        final CupLUT stackOfCups = new CupStackLUT((row, col) -> new WhiskeyCup(CUP_VOLUME, row, col));

        try {
            stackOfCups.fill(-1);

            fail("filling a cup with negative volume is not allowed");
        }
        catch (final InvalidArgumentException ignored) {}
    }

    @Test
    public void cupFillZero() throws InvalidArgumentException, InvalidObjectException {
        final CupLUT stackOfCups = new CupStackLUT((row, col) -> new WhiskeyCup(CUP_VOLUME, row, col));

        stackOfCups.fill(0);

        assertEquals("Excepted Cup(0,0) to contain volume of 0", 0, stackOfCups.getCup(0,0).getCurrentVolume());
    }

    @Test
    public void cupFillUnder() throws InvalidArgumentException, InvalidObjectException {
        final CupLUT stackOfCups = new CupStackLUT((row, col) -> new WhiskeyCup(CUP_VOLUME, row, col));

        stackOfCups.fill(100);

        assertEquals("Excepted Cup(0,0) to contain volume of 100", 100, stackOfCups.getCup(0,0).getCurrentVolume());
    }

    @Test
    public void cupFillExact() throws InvalidArgumentException, InvalidObjectException {
        final CupLUT stackOfCups = new CupStackLUT((row, col) -> new WhiskeyCup(CUP_VOLUME, row, col));

        stackOfCups.fill(250);

        assertEquals("Excepted Cup(0,0) to contain volume of " + CUP_VOLUME, CUP_VOLUME, stackOfCups.getCup(0,0).getCurrentVolume());
    }

    @Test
    public void cupFillOver() throws InvalidArgumentException, InvalidObjectException {
        final CupLUT stackOfCups = new CupStackLUT((row, col) -> new WhiskeyCup(CUP_VOLUME, row, col));

        stackOfCups.fill(300);

        assertEquals("Excepted Cup(0,0) to contain volume of " + CUP_VOLUME, CUP_VOLUME, stackOfCups.getCup(0,0).getCurrentVolume());
    }

    @Test
    public void cupFillOversplit() throws InvalidArgumentException, InvalidObjectException {
        final CupLUT stackOfCups = new CupStackLUT((row, col) -> new WhiskeyCup(CUP_VOLUME, row, col));

        stackOfCups.fill(CUP_VOLUME * 10);

        assertEquals("Excepted Cup(0,0) to contain volume of " + CUP_VOLUME, CUP_VOLUME, stackOfCups.getCup(0,0).getCurrentVolume());
    }

    @Test
    public void cupFillChain() throws InvalidArgumentException, InvalidObjectException {
        final CupLUT stackOfCups = new CupStackLUT((row, col) -> new WhiskeyCup(CUP_VOLUME, row, col));

        stackOfCups.fill(CUP_VOLUME + 217);

        final int expectedLeftVolume = 217 / 2;
        final int expectedRightVolume = 217 - expectedLeftVolume;

        assertEquals("Excepted Cup(0,0) to contain volume of " + CUP_VOLUME, CUP_VOLUME, stackOfCups.getCup(0,0).getCurrentVolume());
        assertEquals("Excepted Cup(1,0) to contain volume of " + expectedLeftVolume, expectedLeftVolume, stackOfCups.getCup(1,0).getCurrentVolume());
        assertEquals("Excepted Cup(1,1) to contain volume of " + expectedRightVolume, expectedRightVolume, stackOfCups.getCup(1,1).getCurrentVolume());
    }

    @Test
    public void cupFillTotal() throws InvalidArgumentException, InvalidObjectException {
        final CupLUT stackOfCups = new CupStackLUT((row, col) -> new WhiskeyCup(CUP_VOLUME, row, col));

        final int totalVolume = (CUP_VOLUME * 10) + 986;

        stackOfCups.fill(totalVolume);

        assertEquals("Expected total volume to be " + totalVolume, totalVolume, stackOfCups.getTotalVolume());
    }

    @Test
    public void cupFillVariableCapacity() throws InvalidArgumentException {
        // variable capacity cups are not allowed in a single stack
        final AtomicInteger variableCupsVolume = new AtomicInteger(CUP_VOLUME);

        final CupLUT stackOfCups = new CupStackLUT((row, col) -> new WhiskeyCup(variableCupsVolume.getAndIncrement(), row, col));

        try {
            stackOfCups.fill(CUP_VOLUME * 10);

            fail("creating cups with variable capacities is not allowed");
        }
        catch (final InvalidObjectException ignored) {}
    }

    @Test
    public void cupFillMultiChain() throws InvalidArgumentException, InvalidObjectException {
        final CupLUT stackOfCups = new CupStackLUT((row, col) -> new WhiskeyCup(CUP_VOLUME, row, col));

        stackOfCups.fill(217);

        assertEquals("Excepted Cup(0,0) to contain volume of 217", 217, stackOfCups.getCup(0,0).getCurrentVolume());
        assertEquals("Expected total volume to be 217", 217, stackOfCups.getTotalVolume());

        stackOfCups.fill(315);

        // 282 is the leftover once root cup is filled to capacity of 250
        final int expectedLeftVolume = 282 / 2;
        final int expectedRightVolume = 282 - expectedLeftVolume;

        assertEquals("Excepted Cup(0,0) to contain volume of " + CUP_VOLUME, CUP_VOLUME, stackOfCups.getCup(0,0).getCurrentVolume());
        assertEquals("Excepted Cup(1,0) to contain volume of " + expectedLeftVolume, expectedLeftVolume, stackOfCups.getCup(1,0).getCurrentVolume());
        assertEquals("Excepted Cup(1,1) to contain volume of " + expectedRightVolume, expectedRightVolume, stackOfCups.getCup(1,1).getCurrentVolume());
        assertEquals("Expected total volume to be 532", 532, stackOfCups.getTotalVolume());
    }

    @Test
    public void cupFillMultiChainEmpty() throws InvalidArgumentException, InvalidObjectException {
        final CupLUT stackOfCups = new CupStackLUT((row, col) -> new WhiskeyCup(CUP_VOLUME, row, col));

        stackOfCups.fill(532);

        // 282 is the leftover once root cup is filled to capacity of 250
        final int expectedLeftVolume = 282 / 2;
        final int expectedRightVolume = 282 - expectedLeftVolume;

        // we discard all of the volume from the root
        stackOfCups.getRootCup().drainCurrentVolume();

        assertEquals("Excepted Cup(0,0) to contain volume of 0", 0, stackOfCups.getCup(0,0).getCurrentVolume());
        assertEquals("Excepted Cup(1,0) to contain volume of " + expectedLeftVolume, expectedLeftVolume, stackOfCups.getCup(1,0).getCurrentVolume());
        assertEquals("Excepted Cup(1,1) to contain volume of " + expectedRightVolume, expectedRightVolume, stackOfCups.getCup(1,1).getCurrentVolume());
        assertEquals("Expected total volume to be 282", 282, stackOfCups.getTotalVolume());

        // re-add some volume back from the root
        stackOfCups.fill(220);

        assertEquals("Excepted Cup(0,0) to contain volume of 220", 220, stackOfCups.getCup(0,0).getCurrentVolume());
        assertEquals("Excepted Cup(1,0) to contain volume of " + expectedLeftVolume, expectedLeftVolume, stackOfCups.getCup(1,0).getCurrentVolume());
        assertEquals("Excepted Cup(1,1) to contain volume of " + expectedRightVolume, expectedRightVolume, stackOfCups.getCup(1,1).getCurrentVolume());
        assertEquals("Expected total volume to be 502", 502, stackOfCups.getTotalVolume());
    }
}
