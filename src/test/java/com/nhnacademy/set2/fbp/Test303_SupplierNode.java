package com.nhnacademy.set2.fbp;

import static org.awaitility.Awaitility.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.fbp.Pipe;
import com.nhnacademy.fbp.SupplierNode;

@TestMethodOrder(OrderAnnotation.class)
public class Test303_SupplierNode {
    static boolean success = false;
    static ObjectMapper mapper = new ObjectMapper();
    static int count = 0;

    @Test
    @Order(1)
    void testSupplieriNode() {
        Stream<Arguments> testSet = Stream.of(
                Arguments.of(2),
                Arguments.of(3),
                Arguments.of(4),
                Arguments.of(5));

        testSet.forEach(args -> testSupplierNode((Integer) args.get()[0]));
        success = true;
    }

    @ParameterizedTest
    @MethodSource("provideResult")
    @Order(2)
    void testSupplierNode2(boolean dummy) {
        assertTrue(success);
    }

    static Stream<Arguments> provideResult() {
        return Stream.of(
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success));
    }

    void testSupplierNode(int timeout) {
        assertDoesNotThrow(() -> {
            count = 0;
            Pipe pipe = new Pipe();
            SupplierNode supplier = new SupplierNode(() -> {
                try {
                    return mapper.readTree(String.format("{\"count\" : %d}", ++count));
                } catch (JsonProcessingException e) {
                    return null;
                }
            });
            supplier.connectOutput(pipe);

            Thread thread = new Thread(supplier);

            thread.start();

            await().atLeast(timeout - 1, TimeUnit.SECONDS)
                    .atMost(timeout + 1, TimeUnit.SECONDS)
                    .until((() -> count > timeout * 10));

            supplier.stop();

            thread.join();
            while (!pipe.isEmpty()) {
                assertNotNull(pipe.poll());
            }
        });
    }
}
