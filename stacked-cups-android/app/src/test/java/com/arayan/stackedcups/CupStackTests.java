package com.arayan.stackedcups;

import com.arayan.stackedcups.model.CupStackLUT;
import com.arayan.stackedcups.model.WhiskeyCup;
import com.arayan.stackedcups.model.exceptions.InvalidArgumentException;
import com.arayan.stackedcups.model.interfaces.Cup;
import com.arayan.stackedcups.model.interfaces.CupLUT;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

public final class CupStackTests {

    @Test
    public void createLutTableNull() {
        try {
            new CupStackLUT(null);

            fail("The construction of a LUT table with a null factory is expected to fail");
        }
        catch (final InvalidArgumentException ignored) {}
    }

    @Test
    public void createLutTableValid() {
        try {
            new CupStackLUT((row, col) -> new WhiskeyCup(250, row, col));
        }
        catch (final Exception e) {
            fail("CupStackLUT should have initialised properly");
        }
    }

    @Test
    public void createLutTableGetRoot() throws InvalidArgumentException {
        final CupLUT lut = new CupStackLUT((row, col) -> new WhiskeyCup(250, row, col));

        final Cup cup = lut.getCup(0,0);
        final Cup rootCup = lut.getRootCup();

        assertTrue("Expected root cup to be valid", rootCup.isValid());
        assertEquals("Excepted Cup(0,0) and root Cup to be equal", cup, rootCup);
    }

    @Test
    public void createLutTableRootVolumeZero() {
        try {
            new CupStackLUT((row, col) -> new WhiskeyCup(0, row, col));

            fail("The construction of a LUT table with a zero capacity cup is expected to fail");
        }
        catch (final InvalidArgumentException ignored) {}
    }

    @Test
    public void createLutTableRootVolumeNegative() {
        try {
            new CupStackLUT((row, col) -> new WhiskeyCup(-1, row, col));

            fail("The construction of a LUT table with a negative capacity cup is expected to fail");
        }
        catch (final InvalidArgumentException ignored) {}
    }
}
