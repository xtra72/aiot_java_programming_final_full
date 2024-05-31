package com.nhnacademy.calculator;

import com.nhnacademy.number.XNumber;

public class NumberNode implements Node {
    XNumber value;

    public NumberNode(XNumber value) {
        this.value = value;
    }

    public XNumber evaluate() {
        return value;
    }

    public String toString(Mode mode) {
        return value.toString();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
