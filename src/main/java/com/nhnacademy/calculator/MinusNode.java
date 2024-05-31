package com.nhnacademy.calculator;

import com.nhnacademy.number.XNumber;

public class MinusNode extends OperatorNode {
    public MinusNode(Node left, Node right) {
        super(left, right, "-");
    }

    public XNumber evaluate() {
        return XNumber.subtract(left.evaluate(), right.evaluate());
    }
}
