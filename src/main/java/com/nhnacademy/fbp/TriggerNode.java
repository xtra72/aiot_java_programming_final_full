package com.nhnacademy.fbp;

import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TriggerNode extends SupplierNode {
    AtomicInteger id = new AtomicInteger();

    public TriggerNode() {
        setSupplier(() -> {
            id.set(id.get() + 1);
            String jsonString = String.format("{\"id\" : %d}", id.get());
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readTree(jsonString);
            } catch (JsonProcessingException e) {
                return null;
            }
        });
    }
}
