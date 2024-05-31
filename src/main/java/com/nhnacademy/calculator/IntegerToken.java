package com.nhnacademy.calculator;

import com.nhnacademy.number.XInteger;
import com.nhnacademy.number.XNumber;

public class IntegerToken extends NumberToken {

    IntegerToken(String value) {
        super(value);
    }

    public XNumber toNumber() {
        return new XInteger(value);
    }
}
