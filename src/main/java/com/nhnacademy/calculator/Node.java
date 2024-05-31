package com.nhnacademy.calculator;

import com.nhnacademy.number.XNumber;

public interface Node {
    enum Mode {
        INFIX,
        PREFIX,
        POSTFIX
    }

    public XNumber evaluate();

    public String toString(Mode mode);
}
