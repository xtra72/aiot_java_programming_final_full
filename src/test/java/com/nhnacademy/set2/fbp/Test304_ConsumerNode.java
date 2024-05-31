package com.nhnacademy.set2.fbp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.awaitility.Awaitility.*;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.fbp.ConsumerNode;
import com.nhnacademy.fbp.Pipe;
import com.nhnacademy.fbp.SupplierNode;

@TestMethodOrder(OrderAnnotation.class)
public class Test304_ConsumerNode {
    static boolean success = false;
    static int count = 0;

    @Test
    @Order(1)
    void testComsumerNode() {
        SupplierNode supplier = new SupplierNode(new Supplier<JsonNode>() {
            @Override
            public JsonNode get() {
                String jsonString = "{}";
                ObjectMapper mapper = new ObjectMapper();
                try {
                    return mapper.readTree(jsonString);
                } catch (JsonProcessingException e) {
                    return null;
                }
            }
        });

        ConsumerNode counter = new ConsumerNode(m -> count++);

        Pipe pipe = new Pipe();

        supplier.connectOutput(pipe);
        counter.connectInput(pipe);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(supplier);
        executor.submit(counter);

        await().atMost(2, TimeUnit.SECONDS).until((() -> count > 10));

        supplier.stop();
        counter.stop();

        executor.shutdown();
        assertTrue(count > 10);

        success = true;
    }

    @ParameterizedTest
    @MethodSource("provideResult")
    @Order(2)
    void testComsumerNode2(boolean dummy) {
        assertTrue(success);
    }

    static Stream<Arguments> provideResult() {
        return Stream.of(
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success));
    }

}
