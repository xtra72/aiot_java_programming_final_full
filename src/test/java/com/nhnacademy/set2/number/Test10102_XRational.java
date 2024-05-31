package com.nhnacademy.set2.number;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;
import java.util.Random;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import com.nhnacademy.number.XNumber;
import com.nhnacademy.number.XRational;

public class Test10102_XRational {
   Random random = new Random();

   @Test
   void testIllegalArgumentExceptionAtCreation() {
      assertThrowsExactly(IllegalArgumentException.class,
            () -> new XRational(null, null));
      assertThrowsExactly(IllegalArgumentException.class,
            () -> new XRational(null, BigInteger.valueOf(1)));
      assertThrowsExactly(IllegalArgumentException.class,
            () -> new XRational(BigInteger.valueOf(1), null));
      assertThrowsExactly(IllegalArgumentException.class,
            () -> new XRational(BigInteger.valueOf(1), BigInteger.valueOf(0)));
      assertThrowsExactly(IllegalArgumentException.class,
            () -> new XRational(1, 0));
      assertThrowsExactly(IllegalArgumentException.class,
            () -> new XRational("1/2"));
   }

   @Test
   void testCreation() {
      XRational r1 = new XRational(BigInteger.valueOf(4), BigInteger.valueOf(6));
      assertEquals(BigInteger.valueOf(2), r1.getNumerator());
      assertEquals(BigInteger.valueOf(3), r1.getDenominator());

      XRational r2 = new XRational(BigInteger.valueOf(10), BigInteger.valueOf(-20));
      assertEquals(BigInteger.valueOf(-1), r2.getNumerator());
      assertEquals(BigInteger.valueOf(2), r2.getDenominator());

      XRational r3 = new XRational(4, 6);
      assertEquals(BigInteger.valueOf(2), r3.getNumerator());
      assertEquals(BigInteger.valueOf(3), r3.getDenominator());

      XRational r4 = new XRational(10, -20);
      assertEquals(BigInteger.valueOf(-1), r4.getNumerator());
      assertEquals(BigInteger.valueOf(2), r4.getDenominator());

      XRational r5 = new XRational("[4,6]");
      assertEquals(BigInteger.valueOf(2), r5.getNumerator());
      assertEquals(BigInteger.valueOf(3), r5.getDenominator());

      XRational r6 = new XRational("[10,-20]");
      assertEquals(BigInteger.valueOf(-1), r6.getNumerator());
      assertEquals(BigInteger.valueOf(2), r6.getDenominator());
   }

   @Test
   void testAdd() {
      provideAdd().forEach(args -> testAdd(
            (Integer) args.get()[0],
            (Integer) args.get()[1],
            (Integer) args.get()[2],
            (Integer) args.get()[3],
            (Integer) args.get()[4],
            (Integer) args.get()[5]));
   }

   void testAdd(long n1, long d1, long n2, long d2, long nt, long dt) {
      XRational r1 = new XRational(n1, d1);
      XRational r2 = new XRational(n2, d2);
      XNumber result = r1.add(r2);
      assertTrue(result instanceof XRational);

      XRational r3 = (XRational) result;
      assertEquals(BigInteger.valueOf(nt), r3.getNumerator());
      assertEquals(BigInteger.valueOf(dt), r3.getDenominator());
   }

   static Stream<Arguments> provideAdd() {
      return Stream.of(
            Arguments.of(1, 3, 1, 3, 2, 3),
            Arguments.of(1, 3, 2, 3, 1, 1),
            Arguments.of(1, 3, 3, 3, 4, 3),
            Arguments.of(1, 3, 4, 3, 5, 3),
            Arguments.of(1, 3, 5, 3, 2, 1),
            Arguments.of(1, 3, -1, 3, 0, 1),
            Arguments.of(1, 3, -2, 3, -1, 3),
            Arguments.of(1, 3, 3, -3, -2, 3),
            Arguments.of(1, 3, 4, -3, -1, 1),
            Arguments.of(Integer.MAX_VALUE - 1, Integer.MAX_VALUE, 1, Integer.MAX_VALUE, 1, 1),
            Arguments.of(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, -Integer.MAX_VALUE, 0, 1));
   }

   @Test
   void testSubtract() {
      provideSubtract().forEach(args -> testSubtract(
            (Integer) args.get()[0],
            (Integer) args.get()[1],
            (Integer) args.get()[2],
            (Integer) args.get()[3],
            (Integer) args.get()[4],
            (Integer) args.get()[5]));
   }

   void testSubtract(long n1, long d1, long n2, long d2, long nt, long dt) {
      XRational r1 = new XRational(n1, d1);
      XRational r2 = new XRational(n2, d2);
      XNumber result = r1.subtract(r2);
      assertTrue(result instanceof XRational);
      XRational r3 = (XRational) result;
      assertEquals(BigInteger.valueOf(nt), r3.getNumerator());
      assertEquals(BigInteger.valueOf(dt), r3.getDenominator());
   }

   static Stream<Arguments> provideSubtract() {
      return Stream.of(
            Arguments.of(1, 3, 1, 3, 0, 1),
            Arguments.of(1, 3, 2, 3, -1, 3),
            Arguments.of(1, 3, 3, 3, -2, 3),
            Arguments.of(1, 3, 4, 3, -1, 1),
            Arguments.of(1, 3, 5, 3, -4, 3),
            Arguments.of(1, 3, -1, 3, 2, 3),
            Arguments.of(1, 3, -2, 3, 1, 1),
            Arguments.of(1, 3, 3, -3, 4, 3),
            Arguments.of(1, 3, 4, -3, 5, 3),
            Arguments.of(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE - 1, Integer.MAX_VALUE, 1,
                  Integer.MAX_VALUE),
            Arguments.of(-1, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE,
                  Integer.MAX_VALUE));
   }

   @Test
   void testMultiply() {
      XRational r1 = new XRational(2, 3);
      XRational r2 = new XRational(3, 4);
      XNumber result = r1.multiply(r2);
      assertTrue(result instanceof XRational);
      XRational r3 = (XRational) result;
      assertEquals(BigInteger.valueOf(1), r3.getNumerator());
      assertEquals(BigInteger.valueOf(2), r3.getDenominator());
   }

   @Test
   void testDivide() {
      XRational r1 = new XRational(1, 2);
      XRational r2 = new XRational(3, 4);
      XNumber result = r1.divide(r2);
      assertTrue(result instanceof XRational);
      XRational r3 = (XRational) result;
      assertEquals(BigInteger.valueOf(2), r3.getNumerator());
      assertEquals(BigInteger.valueOf(3), r3.getDenominator());
   }

   @Test
   void testToString() {
      provideToString().forEach(args -> testToString(
            (Integer) args.get()[0],
            (Integer) args.get()[1],
            (String) args.get()[2]));
   }

   void testToString(long numerator, long denominator, String target) {
      XRational r1 = new XRational(numerator, denominator);
      assertEquals(target, r1.toString());
   }

   static Stream<Arguments> provideToString() {
      return Stream.of(
            Arguments.of(2, 3, "[2,3]"),
            Arguments.of(-2, 3, "[-2,3]"),
            Arguments.of(2, -3, "[-2,3]"),
            Arguments.of(-2, -3, "[2,3]"),
            Arguments.of(4, 6, "[2,3]"),
            Arguments.of(-6, 9, "[-2,3]"),
            Arguments.of(8, -12, "[-2,3]"),
            Arguments.of(-10, -15, "[2,3]"));
   }

   @Test
   void testEquals() {
      provideEqualsAndHashCode().forEach(args -> testEquals(
            (Integer) args.get()[0],
            (Integer) args.get()[1],
            (Integer) args.get()[2],
            (Integer) args.get()[3]));
   }

   void testEquals(long numerator1, long denominator1, long numerator2, long denominator2) {
      XRational r1 = new XRational(numerator1, denominator1);
      XRational r2 = new XRational(numerator2, denominator2);
      assertEquals(r1, r2);
   }

   @Test
   void testHashCode() {
      provideEqualsAndHashCode().forEach(args -> testHashCode(
            (Integer) args.get()[0],
            (Integer) args.get()[1],
            (Integer) args.get()[2],
            (Integer) args.get()[3]));
   }

   void testHashCode(long numerator1, long denominator1, long numerator2, long denominator2) {
      XRational r1 = new XRational(numerator1, denominator1);
      XRational r2 = new XRational(numerator2, denominator2);
      assertEquals(r1.hashCode(), r2.hashCode());
   }

   static Stream<Arguments> provideEqualsAndHashCode() {
      return Stream.of(
            Arguments.of(2, 3, 4, 6),
            Arguments.of(2, -3, -4, 6),
            Arguments.of(-2, 3, 8, -12),
            Arguments.of(-2, -3, 2, 3),
            Arguments.of(-2, -3, -4, -6));
   }
}
