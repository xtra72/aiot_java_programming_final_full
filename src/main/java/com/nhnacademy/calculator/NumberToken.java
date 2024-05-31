package com.nhnacademy.calculator;

import com.nhnacademy.number.XInteger;
import com.nhnacademy.number.XNumber;
import com.nhnacademy.number.XRational;

public class NumberToken implements Token {
    String value;

    NumberToken(String value) {
        this.value = value;
    }

    public XNumber toNumber() {
        if (value.matches(XInteger.PATTERN)) {
            return new XInteger(value);
        } else {
            return new XRational(value);
        }
    }

    public int getPriority() {
        return 0;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof NumberToken) && value.equals(((NumberToken) other).toString());
    }

    public boolean equals(String other) {
        return value.equals(other);
    }
}
