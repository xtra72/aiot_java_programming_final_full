package com.nhnacademy.fbp;

import java.util.function.UnaryOperator;

import com.fasterxml.jackson.databind.JsonNode;

public class FilterNode extends Node {

    Pipe input;
    Pipe output;
    UnaryOperator<JsonNode> operator;

    public FilterNode(UnaryOperator<JsonNode> operator) {
        this.operator = operator;
    }

    public void connectInput(Pipe input) {
        this.input = input;
    }

    public void connectOutput(Pipe output) {
        this.output = output;
    }

    @Override
    public void process() {
        if ((input != null) && (output != null) && !input.isEmpty()) {
            JsonNode message = input.poll();

            JsonNode result = operator.apply(message);
            if (result != null) {
                output.push(result);
            }
        }
    }

}
