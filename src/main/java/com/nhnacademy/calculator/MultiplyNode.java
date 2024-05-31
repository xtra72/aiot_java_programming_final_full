package com.nhnacademy.calculator;

import com.nhnacademy.number.XNumber;

public class MultiplyNode extends OperatorNode {
    public MultiplyNode(Node left, Node right) {
        super(left, right, "*");
    }

    public XNumber evaluate() {
        return XNumber.multiply(left.evaluate(), right.evaluate());
    }
}
