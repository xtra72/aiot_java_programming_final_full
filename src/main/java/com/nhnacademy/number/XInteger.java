package com.nhnacademy.number;

import java.math.BigInteger;

import com.nhnacademy.exception.DivideByZeroException;

/**
 * 정수를 표현하기 위한 클래스를 정의합니다.
 * - 모든 정수는 유리수로 표현 가능합니다.
 */
public class XInteger extends XRational {
    public static final String PATTERN = "[-+]?\\d+";

    /**
     * 자바의 정수형을 받아 정수 오브젝트를 생성합니다.
     * 
     * @param value
     */
    public XInteger(long value) {
        super(BigInteger.valueOf(value), BigInteger.ONE);
    }

    /**
     * 자바의 무한 정수형을 받아 정수 오브젝트를 생성합니다.
     * 
     * @param value
     */
    public XInteger(BigInteger value) {
        super(value, BigInteger.ONE);
    }

    /**
     * 문자열을 받아 정수 오브젝트를 생성합니다.
     * 
     * @param value
     */
    public XInteger(String value) {
        super(new BigInteger(value), BigInteger.ONE);
    }

    /**
     * 정수 오브젝트의 값을 자바의 무한 정수형으로 돌려 줍니다.
     * 
     * @return 무한 정수형 값
     */
    public BigInteger getValue() {
        return getNumerator();
    }

    /**
     * 정수를 표현하는 문자열인지 확인합니다.
     * 
     * @param value
     * @return
     */
    public static boolean isValid(String value) {
        return (value != null) && (value.matches(XInteger.PATTERN));
    }

    @Override
    public XNumber add(XNumber other) {
        if (other == null) {
            throw new NullPointerException();
        }

        if (other instanceof XInteger) {
            return new XInteger(getValue().add(((XInteger) other).getValue()));
        } else {
            return super.add(other);
        }
    }

    @Override
    public XNumber subtract(XNumber other) {
        if (other == null) {
            throw new NullPointerException();
        }

        if (other instanceof XInteger) {
            return new XInteger(getValue().subtract(((XInteger) other).getValue()));
        } else {
            return super.subtract(other);
        }
    }

    @Override
    public XNumber multiply(XNumber other) {
        if (other == null) {
            throw new NullPointerException();
        }

        if (other instanceof XInteger) {
            return new XInteger(getValue().multiply(((XInteger) other).getValue()));
        } else {
            return super.multiply(other);
        }
    }

    @Override
    public XNumber divide(XNumber other) {
        if (other == null) {
            throw new NullPointerException();
        }

        if (other instanceof XInteger) {
            if (((XInteger) other).getValue().equals(BigInteger.ZERO)) {
                throw new DivideByZeroException();
            }

            return new XInteger(getValue().divide(((XInteger) other).getValue()));
        } else {
            return super.divide(other);
        }
    }
}