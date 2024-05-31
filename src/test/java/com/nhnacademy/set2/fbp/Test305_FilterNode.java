package com.nhnacademy.set2.fbp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
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
import com.nhnacademy.fbp.FilterNode;
import com.nhnacademy.fbp.Pipe;
import com.nhnacademy.fbp.SupplierNode;

@TestMethodOrder(OrderAnnotation.class)
public class Test305_FilterNode {
    static boolean success = false;
    static int count = 0;

    @Test
    @Order(1)
    void testFilterNode() {
        AtomicInteger id = new AtomicInteger(0);

        SupplierNode trigger = new SupplierNode(new Supplier<JsonNode>() {
            @Override
            public JsonNode get() {
                try {
                    return new ObjectMapper().readTree(String.format("{\"id\" : %d }", id.incrementAndGet()));
                } catch (JsonProcessingException e) {
                    return null;
                }
            }
        });
        FilterNode filter = new FilterNode(x -> {
            JsonNode idNode = x.get("id");
            if ((idNode != null) && idNode.isInt() && ((idNode.asInt() % 2) == 0)) {
                return x;
            } else {
                return null;
            }
        });
        ConsumerNode counter = new ConsumerNode(m -> count++);

        Pipe pipe1 = new Pipe();
        Pipe pipe2 = new Pipe();

        trigger.connectOutput(pipe1);
        filter.connectInput(pipe1);
        filter.connectOutput(pipe2);
        counter.connectInput(pipe2);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.submit(filter);
        executor.submit(trigger);
        executor.submit(counter);

        await().atMost(5, TimeUnit.SECONDS).until((() -> count > 10));

        filter.stop();
        trigger.stop();
        counter.stop();

        executor.shutdown();
        assertTrue(id.get() > 20);
        assertTrue(count > 10);

        success = true;
    }

    @ParameterizedTest
    @MethodSource("provideResult")
    @Order(2)
    void testFilerNode2(boolean dummy) {
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
