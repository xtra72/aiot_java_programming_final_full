package com.nhnacademy.fbp;

import com.fasterxml.jackson.databind.JsonNode;

public class EvenFilterNode extends FilterNode {

    public EvenFilterNode() {
        super(x -> {
            JsonNode id = x.get("id");
            if (id != null) {
                return x;
            } else {
                return null;
            }
        });
    }
}
