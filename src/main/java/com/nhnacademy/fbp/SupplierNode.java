package com.nhnacademy.fbp;

import java.util.function.Supplier;

import com.fasterxml.jackson.databind.JsonNode;

public class SupplierNode extends Node {
    Pipe output;
    Supplier<JsonNode> action;

    public SupplierNode() {
    }

    public SupplierNode(Supplier<JsonNode> action) {
        this.action = action;
    }

    public void setSupplier(Supplier<JsonNode> action) {
        this.action = action;
    }

    public void connectOutput(Pipe output) {
        this.output = output;
    }

    @Override
    public void process() {
        if (output != null) {
            output.push(action.get());
        }
    }

}
