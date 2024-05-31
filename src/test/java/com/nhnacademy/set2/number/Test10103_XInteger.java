package com.nhnacademy.set2.number;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import com.nhnacademy.number.XInteger;
import com.nhnacademy.number.XNumber;
import com.nhnacademy.number.XRational;

public class Test10103_XInteger {
    @Test
    void testCreation() {
        XInteger i1 = new XInteger(1);
        XInteger i2 = new XInteger(BigInteger.valueOf(1));
        XInteger i3 = new XInteger(-1);
        XInteger i4 = new XInteger(BigInteger.valueOf(-1));

        assertEquals(BigInteger.ONE, i1.getValue());
        assertEquals(BigInteger.ONE, i2.getValue());
        assertEquals(BigInteger.ZERO.subtract(BigInteger.ONE), i3.getValue());
        assertEquals(BigInteger.ZERO.subtract(BigInteger.ONE), i4.getValue());
    }

    @Test
    void testAddTwoInteger() {
        provideAddTwoInteger().forEach(args -> testAddTwoInteger(
                (Integer) args.get()[0],
                (Integer) args.get()[1],
                (Integer) args.get()[2]));
    }

    void testAddTwoInteger(int operand1, int operand2, int target) {
        XInteger i1 = new XInteger(operand1);
        XInteger i2 = new XInteger(operand2);
        XInteger t1 = new XInteger(target);

        XNumber result1 = i1.add(i2);
        assertTrue(result1 instanceof XInteger);
        assertEquals(t1, result1);
    }

    static Stream<Arguments> provideAddTwoInteger() {
        return Stream.of(
                Arguments.of(1, 1, 2),
                Arguments.of(1, 100, 101),
                Arguments.of(0, 1, 1),
                Arguments.of(1, 0, 1),
                Arguments.of(1, -1, 0),
                Arguments.of(-1, 1, 0),
                Arguments.of(-1, -100, -101),
                Arguments.of(-1, -1, -2));
    }

    @Test
    void testAddIntegerAndRational() {
        provideAddIntegerAndRational().forEach(args -> testAddIntegerAndRational(
                (Integer) args.get()[0],
                (String) args.get()[1],
                (String) args.get()[2]));
    }

    void testAddIntegerAndRational(int operand1, String operand2, String target) {
        XInteger i1 = new XInteger(operand1);
        XRational r1 = new XRational(operand2);
        XRational t1 = new XRational(target);

        XNumber result1 = i1.add(r1);
        assertTrue(result1 instanceof XRational);
        assertEquals(t1, result1);
    }

    static Stream<Arguments> provideAddIntegerAndRational() {
        return Stream.of(
                Arguments.of(1, "[1,1]", "2"),
                Arguments.of(1, "[1,100]", "[101,100]"),
                Arguments.of(0, "1", "1"),
                Arguments.of(1, "[+1,1]", "2"),
                Arguments.of(1, "[+1,100]", "[101,100]"),
                Arguments.of(0, "+1", "1"),
                Arguments.of(1, "[1,+1]", "2"),
                Arguments.of(1, "[1,+100]", "[101,100]"),
                Arguments.of(1, "[+1,+1]", "2"),
                Arguments.of(1, "[+1,+100]", "[101,100]"),
                Arguments.of(1, "0", "1"),
                Arguments.of(1, "[-1,1]", "0"),
                Arguments.of(-1, "1", "0"),
                Arguments.of(-1, "[-1,100]", "[-101,100]"),
                Arguments.of(-1, "[-10,100]", "[-11,10]"));
    }

    @Test
    void testSubtractTwoInteger() {
        provideSubtractTwoInteger().forEach(args -> testSubtractTwoInteger(
                (Integer) args.get()[0],
                (Integer) args.get()[1],
                (Integer) args.get()[2]));
    }

    void testSubtractTwoInteger(int operand1, int operand2, int target) {
        XInteger i1 = new XInteger(operand1);
        XInteger i2 = new XInteger(operand2);
        XInteger t1 = new XInteger(target);

        XNumber result1 = i1.subtract(i2);
        assertTrue(result1 instanceof XInteger);
        assertEquals(t1, result1);
    }

    static Stream<Arguments> provideSubtractTwoInteger() {
        return Stream.of(
                Arguments.of(1, 1, 0),
                Arguments.of(10, 1, 9),
                Arguments.of(0, 1, -1),
                Arguments.of(1, -1, 2),
                Arguments.of(-1, -1, 0));
    }

    @Test
    void testSubtractIntegerAndRational() {
        provideSubtractIntegerAndRational().forEach(args -> testSubtractIntegerAndRational(
                (Integer) args.get()[0],
                (String) args.get()[1],
                (String) args.get()[2]));
    }

    void testSubtractIntegerAndRational(int operand1, String operand2, String target) {
        XInteger i1 = new XInteger(operand1);
        XRational r1 = new XRational(operand2);
        XRational t1 = new XRational(target);

        XNumber result1 = i1.subtract(r1);
        assertTrue(result1 instanceof XRational);
        assertEquals(t1, result1);
    }

    static Stream<Arguments> provideSubtractIntegerAndRational() {
        return Stream.of(
                Arguments.of(1, "[1,1]", "0"),
                Arguments.of(1, "[1,100]", "[99,100]"),
                Arguments.of(0, "1", "-1"),
                Arguments.of(1, "[+1,1]", "0"),
                Arguments.of(1, "[+1,100]", "[99,100]"),
                Arguments.of(0, "+1", "-1"),
                Arguments.of(1, "[1,+1]", "0"),
                Arguments.of(1, "[1,+100]", "[99,100]"),
                Arguments.of(1, "[+1,+1]", "0"),
                Arguments.of(1, "[+1,+100]", "[99,100]"),
                Arguments.of(1, "0", "1"),
                Arguments.of(1, "[-1,1]", "2"),
                Arguments.of(-1, "1", "-2"),
                Arguments.of(-1, "[-1,100]", "[-99,100]"),
                Arguments.of(-1, "[-10,100]", "[-9,10]"));
    }

    @Test
    void testMultiplyTwoInteger() {
        provideMultiplyTwoInteger().forEach(args -> testMultiplyTwoInteger(
                (Integer) args.get()[0],
                (Integer) args.get()[1],
                (Integer) args.get()[2]));
    }

    void testMultiplyTwoInteger(int operand1, int operand2, int target) {
        XInteger i1 = new XInteger(operand1);
        XInteger i2 = new XInteger(operand2);
        XInteger t1 = new XInteger(target);

        XNumber result1 = i1.multiply(i2);
        assertTrue(result1 instanceof XInteger);
        assertEquals(t1, result1);
    }

    static Stream<Arguments> provideMultiplyTwoInteger() {
        return Stream.of(
                Arguments.of(1, 0, 0),
                Arguments.of(0, 1, 0),
                Arguments.of(-1, 0, 0),
                Arguments.of(0, -1, 0),
                Arguments.of(1, 1, 1),
                Arguments.of(-1, 1, -1),
                Arguments.of(1, -1, -1),
                Arguments.of(-1, -1, 1),
                Arguments.of(9, 9, 81),
                Arguments.of(9, -9, -81),
                Arguments.of(-9, 9, -81),
                Arguments.of(-9, -9, 81));
    }

    @Test
    void testDivideTwoIntger() {
        provideDivideTwoInteger().forEach(args -> testDivideTwoInteger(
                (Integer) args.get()[0],
                (Integer) args.get()[1],
                (Integer) args.get()[2]));
    }

    void testDivideTwoInteger(int operand1, int operand2, int target) {
        XInteger i1 = new XInteger(operand1);
        XInteger i2 = new XInteger(operand2);
        XInteger t1 = new XInteger(target);

        XNumber result1 = i1.divide(i2);
        assertTrue(result1 instanceof XInteger);
        assertEquals(t1, result1);
    }

    static Stream<Arguments> provideDivideTwoInteger() {
        return Stream.of(
                Arguments.of(0, 1, 0),
                Arguments.of(1, 1, 1),
                Arguments.of(-1, 1, -1),
                Arguments.of(1, -1, -1),
                Arguments.of(-1, -1, 1),
                Arguments.of(9, 9, 1),
                Arguments.of(9, -9, -1),
                Arguments.of(-9, 9, -1),
                Arguments.of(1, 9, 0),
                Arguments.of(1, -9, 0),
                Arguments.of(-1, 9, 0),
                Arguments.of(-1, -9, 0));
    }

}
