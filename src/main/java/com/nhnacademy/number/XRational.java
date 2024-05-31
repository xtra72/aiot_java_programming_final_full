package com.nhnacademy.number;

import java.math.BigInteger;

/**
 * 유리수를 표현하기 위한 클래스를 정의합니다.
 * - XNumber의 subclass 입니다.
 * - 무한대의 정수를 이용해 분자와 분모로 표현됩니다.
 * - 분모는 생성시에 음수로 주어질 수 있지만, 음수로 표현될 수는 없습니다.
 */
public class XRational extends XNumber {
    public static final String PATTERN = "\\[\\s*[-+]?\\d+\\s*,\\s*[-+]?\\d+\\s*\\]";
    private BigInteger numerator;
    private BigInteger denominator;

    /**
     * 무한대의 정수 분자와 분모로 생성 가능합니다.
     *
     * @param numerator   분자
     * @param denominator 분모(0은 될 수 없음)
     * @exception IllegalArgumentException 인수가 null이거나, 분수 표현이 불가능한 경우 발생합니다.
     */
    public XRational(BigInteger numerator, BigInteger denominator) {
        if (numerator == null || denominator == null) {
            throw new IllegalArgumentException("인수는 null이 될 수 없습니다.");
        }

        if (denominator.equals(BigInteger.ZERO)) {
            throw new IllegalArgumentException("분모는 0이 될 수 없습니다.");
        }

        if (denominator.compareTo(BigInteger.ZERO) < 0) {
            numerator = numerator.negate();
            denominator = denominator.negate();
        }

        BigInteger gcd = numerator.gcd(denominator);
        this.numerator = numerator.divide(gcd);
        this.denominator = denominator.divide(gcd);
    }

    /**
     * 자바의 정수형으로 분자와 분모로 생성 가능합니다.
     *
     * @param numer분자
     * @param denominator 분모(0은 될 수 없음)
     * @exception IllegalArgumentException 인수가 null이거나, 분수 표현이 불가능한 경우 발생합니다.
     */
    public XRational(long numerator, long denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("분모는 0이 될 수 없습니다.");
        }
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }

        long gcd = gcd(Math.abs(numerator), Math.abs(denominator));
        this.numerator = BigInteger.valueOf(numerator / gcd);
        this.denominator = BigInteger.valueOf(denominator / gcd);
    }

    /**
     * 유리수를 표현하는 문자열을 받아 유리수를 생성합니다.
     * 유리수는 "[분자,분모]" 또는 "정수"의 형태를 갖습니다.
     * 
     * @param value 유리수를 나타내는 문자열
     */
    public XRational(String value) {
        if (value.matches(XRational.PATTERN)) {
            value = value.substring(1, value.length() - 1);
            String[] parts = value.split(",");
            BigInteger newNumerator = new BigInteger(parts[0].trim());
            BigInteger newDenominator = new BigInteger(parts[1].trim());
            if (newDenominator.compareTo(BigInteger.ZERO) < 0) {
                newNumerator = newNumerator.negate();
                newDenominator = newDenominator.negate();
            }

            BigInteger gcd = newNumerator.gcd(newDenominator);
            this.numerator = newNumerator.divide(gcd);
            this.denominator = newDenominator.divide(gcd);
        } else if (value.matches(XInteger.PATTERN)) {
            int n = Integer.parseInt(value);

            this.numerator = BigInteger.valueOf(n);
            this.denominator = BigInteger.ONE;
        } else {
            throw new IllegalArgumentException("Invalid format for rational number or integer.");
        }
    }

    /**
     * 유리수를 표현하는 문자열인지 확인합니다.
     * 
     * @param value
     * @return 유리수 가능 여부
     */
    public static boolean isValid(String value) {
        return (value != null) && (value.matches(XRational.PATTERN));
    }

    /**
     * 두 정수의 최대 공약수를 계산합니다.
     *
     * @param a
     * @param b
     * @return 최대 공약수
     */
    private long gcd(long a, long b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    /**
     * 분자를 돌려 줍니다.
     * 
     * @return 분자
     */
    public BigInteger getNumerator() {
        return numerator;
    }

    /**
     * 분모를 돌려 줍니다.
     * 
     * @return 분모
     */
    public BigInteger getDenominator() {
        return denominator;
    }

    @Override
    public XNumber add(XNumber other) {
        if (other instanceof XRational) {
            XRational o = (XRational) other;
            BigInteger newNumerator = numerator.multiply(o.getDenominator())
                    .add(o.getNumerator().multiply(denominator));
            BigInteger newDenominator = denominator.multiply(o.getDenominator());
            return new XRational(newNumerator, newDenominator);
        }
        throw new IllegalArgumentException("타입이 맞지 않습니다.");
    }

    @Override
    public XNumber subtract(XNumber other) {
        if (other instanceof XRational) {
            XRational o = (XRational) other;
            BigInteger newNumerator = numerator.multiply(o.getDenominator())
                    .subtract(o.getNumerator().multiply(denominator));
            BigInteger newDenominator = denominator.multiply(o.getDenominator());
            return new XRational(newNumerator, newDenominator);
        }
        throw new IllegalArgumentException("타입이 맞지 않습니다.");
    }

    @Override
    public XNumber multiply(XNumber other) {
        if (other instanceof XRational) {
            XRational o = (XRational) other;
            return new XRational(numerator.multiply(o.getNumerator()), denominator.multiply(o.getDenominator()));
        }
        throw new IllegalArgumentException("타입이 맞지 않습니다.");
    }

    @Override
    public XNumber divide(XNumber other) {
        if (other instanceof XRational) {
            XRational o = (XRational) other;
            return new XRational(numerator.multiply(o.getDenominator()), denominator.multiply(o.getNumerator()));
        }
        throw new IllegalArgumentException("타입이 맞지 않습니다.");
    }

    /**
     * 유리수를 문자열로 출력합니다.
     *
     * 정수로 표현이 가능한 경우, 정수 형태로 출력합니다.
     * 분수는 "[분자,분모]"의 형태로 출력합니다.
     * 분수는 기약 분수로 출력합니다.
     */
    @Override
    public String toString() {
        if (denominator.equals(BigInteger.ONE)) {
            return String.valueOf(numerator);
        }
        return "[" + numerator + "," + denominator + "]";
    }

    /**
     * 동일한 값을 표현하는 유리수 인지 확인합니다.
     * 
     * @return true - 동일한 값이 경우, false - 그외
     */
    @Override
    public boolean equals(Object other) {
        return (other instanceof XRational)
                && numerator.equals(((XRational) other).getNumerator())
                && denominator.equals(((XRational) other).getDenominator());
    }

    @Override
    public int hashCode() {
        return numerator.intValue() + denominator.intValue();
    }
}
