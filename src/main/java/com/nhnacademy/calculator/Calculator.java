package com.nhnacademy.calculator;

import java.util.function.BinaryOperator;

import com.nhnacademy.number.XNumber;

public class Calculator {
    Tokenizer tokenizer;
    SyntaxTreeParser parser;

    public Calculator() {
        tokenizer = new Tokenizer();
        parser = new SyntaxTreeParser();
    }

    public void addBinaryOperator(String operator, BinaryOperator<Node> action, int priority) {
        tokenizer.addOperator(operator, priority);
        parser.addOperator(operator, action);
    }

    public XNumber evaluate(String expression) {
        return parser.parse(tokenizer.evaluate(expression)).evaluate();
    }
}
