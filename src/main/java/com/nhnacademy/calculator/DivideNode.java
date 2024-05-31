package com.nhnacademy.calculator;

import com.nhnacademy.number.XNumber;

public class DivideNode extends OperatorNode {
    public DivideNode(Node left, Node right) {
        super(left, right, "/");
    }

    public XNumber evaluate() {
        return XNumber.divide(left.evaluate(), right.evaluate());
    }
}
