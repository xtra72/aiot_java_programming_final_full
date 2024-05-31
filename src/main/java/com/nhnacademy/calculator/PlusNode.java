package com.nhnacademy.calculator;

import com.nhnacademy.number.XNumber;

public class PlusNode extends OperatorNode {
    public PlusNode(Node left, Node right) {
        super(left, right, "+");
    }

    public XNumber evaluate() {
        return XNumber.add(left.evaluate(), right.evaluate());
    }
}
