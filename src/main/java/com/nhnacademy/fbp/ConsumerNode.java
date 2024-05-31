package com.nhnacademy.fbp;

import java.util.function.Consumer;

import com.fasterxml.jackson.databind.JsonNode;

public class ConsumerNode extends Node {
    Pipe input;
    Consumer<JsonNode> action;

    public ConsumerNode(Consumer<JsonNode> action) {
        this.action = action;
    }

    public void connectInput(Pipe input) {
        this.input = input;
    }

    @Override
    public void process() {
        if ((input != null) && (!input.isEmpty())) {
            action.accept(input.poll());
        }
    }

}
