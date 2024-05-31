package com.nhnacademy.calculator;

public interface Token {
    public boolean equals(String word);

    public int getPriority();
}
