package com.nhnacademy.calculator;

import com.nhnacademy.number.XNumber;
import com.nhnacademy.number.XRational;

public class RationalToken extends NumberToken {

    RationalToken(String value) {
        super(value);
    }

    public XNumber toNumber() {
        return new XRational(value);
    }
}
