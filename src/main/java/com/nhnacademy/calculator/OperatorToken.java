package com.nhnacademy.calculator;

public class OperatorToken implements Token {
    int priority = 1;
    String symbol;

    public OperatorToken(String symbol, int priority) {
        this.symbol = symbol;
        this.priority = priority;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return symbol;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof OperatorToken)
                && symbol.equals(((OperatorToken) other).toString());
    }

    public boolean equals(String other) {
        return symbol.equals(other);
    }
}
