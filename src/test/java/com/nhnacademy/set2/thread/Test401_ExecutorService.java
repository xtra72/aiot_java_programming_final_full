package com.nhnacademy.set2.thread;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.nhnacademy.thread.Counter;

@TestMethodOrder(OrderAnnotation.class)
public class Test401_ExecutorService {
    static boolean success = false;

    @Test
    @Order(1)
    void testExecutorService() {
        provideExecutorService().forEach(args -> testExecutorService(
                (Integer) args.get()[0],
                (Integer) args.get()[1]));

        success = true;
    }

    @ParameterizedTest
    @MethodSource("provideResult")
    @Order(2)
    void testExecutorService2(boolean dummy) {
        assertTrue(success);
    }

    static Stream<Arguments> provideResult() {
        return Stream.of(
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success),
                Arguments.of(success));
    }

    void testExecutorService(int poolSize, int instanceCount) {
        assertDoesNotThrow(() -> {
            Counter.clear();

            ExecutorService executor = Executors.newFixedThreadPool(poolSize);

            for (int i = 0; i < instanceCount; i++) {
                executor.execute(new Counter());
            }

            executor.shutdown();

            assertTrue(executor.awaitTermination(2, TimeUnit.SECONDS));
            assertEquals(poolSize, Counter.getCounts().length);
            assertEquals(instanceCount, Counter.getTotalCount());
            assertEquals(instanceCount, Arrays.stream(Counter.getCounts()).reduce(0, (a, b) -> a + b));
        });
    }

    static Stream<Arguments> provideExecutorService() {
        return Stream.of(
                Arguments.of(1, 10),
                Arguments.of(2, 20),
                Arguments.of(3, 30),
                Arguments.of(4, 40),
                Arguments.of(5, 50),
                Arguments.of(6, 60),
                Arguments.of(7, 70),
                Arguments.of(8, 80),
                Arguments.of(9, 90));
    }
}
