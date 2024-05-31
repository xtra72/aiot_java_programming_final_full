package com.nhnacademy.set2.number;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.nhnacademy.number.XInteger;
import com.nhnacademy.number.XNumber;
import com.nhnacademy.number.XRational;

public class Test10101_XNumber {
    @Test
    void testBasicAdd() {
        XNumber r1 = new XRational(-3, 2);
        XNumber r2 = new XRational(2, 3);
        XNumber i1 = new XInteger(-3);
        XNumber i2 = new XInteger(3);
        XNumber result1 = XNumber.add(r1, r2);
        XNumber result2 = XNumber.add(r1, i2);
        XNumber result3 = XNumber.add(i1, r2);
        XNumber result4 = XNumber.add(i1, i2);

        assertTrue(result1 instanceof XRational);
        assertEquals(result1, new XRational(-5, 6));
        assertTrue(result2 instanceof XRational);
        assertEquals(result2, new XRational(3, 2));
        assertTrue(result3 instanceof XRational);
        assertEquals(result3, new XRational(-7, 3));
        assertTrue(result4 instanceof XInteger);
        assertEquals(result4, new XInteger(0));
    }

    @Test
    void testBasicSubtract() {
        XNumber r1 = new XRational(-3, 2);
        XNumber r2 = new XRational(2, 3);
        XNumber i1 = new XInteger(-3);
        XNumber i2 = new XInteger(3);
        XNumber result1 = XNumber.subtract(r1, r2);
        XNumber result2 = XNumber.subtract(r1, i2);
        XNumber result3 = XNumber.subtract(i1, r2);
        XNumber result4 = XNumber.subtract(i1, i2);

        assertTrue(result1 instanceof XRational);
        assertEquals(result1, new XRational(-13, 6));
        assertTrue(result2 instanceof XRational);
        assertEquals(result2, new XRational(-9, 2));
        assertTrue(result3 instanceof XRational);
        assertEquals(result3, new XRational(-11, 3));
        assertTrue(result4 instanceof XInteger);
        assertEquals(result4, new XInteger(-6));
    }

    @Test
    void testBasicMultiply() {
        XNumber r1 = new XRational(-3, 2);
        XNumber r2 = new XRational(2, 3);
        XNumber i1 = new XInteger(-3);
        XNumber i2 = new XInteger(3);
        XNumber result1 = XNumber.multiply(r1, r2);
        XNumber result2 = XNumber.multiply(r1, i2);
        XNumber result3 = XNumber.multiply(i1, r2);
        XNumber result4 = XNumber.multiply(i1, i2);

        assertTrue(result1 instanceof XRational);
        assertEquals(result1, new XRational(-1, 1));
        assertTrue(result2 instanceof XRational);
        assertEquals(result2, new XRational(-9, 2));
        assertTrue(result3 instanceof XRational);
        assertEquals(result3, new XRational(-2, 1));
        assertTrue(result4 instanceof XInteger);
        assertEquals(result4, new XInteger(-9));
    }

    @Test
    void testBasicDivide() {
        XNumber r1 = new XRational(-3, 2);
        XNumber r2 = new XRational(2, 3);
        XNumber i1 = new XInteger(-3);
        XNumber i2 = new XInteger(3);
        XNumber result1 = XNumber.divide(r1, r2);
        XNumber result2 = XNumber.divide(r1, i2);
        XNumber result3 = XNumber.divide(i1, r2);
        XNumber result4 = XNumber.divide(i1, i2);

        assertTrue(result1 instanceof XRational);
        assertEquals(result1, new XRational(-9, 4));
        assertTrue(result2 instanceof XRational);
        assertEquals(result2, new XRational(-1, 2));
        assertTrue(result3 instanceof XRational);
        assertEquals(result3, new XRational(-9, 2));
        assertTrue(result4 instanceof XInteger);
        assertEquals(result4, new XInteger(-1));
    }
}
