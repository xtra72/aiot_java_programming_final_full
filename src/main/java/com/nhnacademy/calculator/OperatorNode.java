package com.nhnacademy.calculator;

import com.nhnacademy.exception.UnsupportedModeException;

public abstract class OperatorNode implements Node {
    Node left;
    Node right;
    String operator;

    OperatorNode(Node left, Node right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public String toString(Mode mode) {
        switch (mode) {
            case INFIX:
                return String.format("(%s %s %s)", left.toString(mode), operator, right.toString(mode));
            case PREFIX:
                return String.format("(%s %s %s)", operator, left.toString(mode), right.toString(mode));
            case POSTFIX:
                return String.format("(%s %s %s)", left.toString(mode), right.toString(mode), operator);
        }

        throw new UnsupportedModeException();
    }

    @Override
    public String toString() {
        return String.format("(%s %s %s)", left, operator, right);
    }
}
