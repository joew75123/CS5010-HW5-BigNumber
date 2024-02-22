package bignumber;

import org.junit.Before;
import org.junit.Test;
import bignumber.*;

import static org.junit.Assert.*;

public class BigNumberImplTest {

    @Test
    public void testLeadingZerosStringConstructor() {
        // Initialize BigNumber with a string with leading zeros
        BigNumberImpl number = new BigNumberImpl("001234");
        //System.out.println(number.toString());
        assertEquals("constructor does not work as expected.", "1234", number.toString());
        assertEquals("constructor does not work as expected.", 4, number.length());
    }

    @Test
    public void testShiftLeft() {
        BigNumberImpl number = new BigNumberImpl("3");
        // Shift left by 9 positions
        number.shiftLeft(9);

        // Convert the result to a string and assert its correctness
        String result = number.toString();
        String expected = "3000000000";

        assertEquals("Left shifting by 9 does not work as expected.", expected, result);
    }

    @Test
    public void shiftRight() {
    }

    @Test
    public void addDigit() {
    }

    @Test
    public void getDigitAt() {
    }

    @Test
    public void copy() {
    }
}