package com.nhnacademy.set2.thread;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.awaitility.Awaitility.*;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.nhnacademy.thread.Waiter;

@TestMethodOrder(OrderAnnotation.class)
public class Test402_Waiter {
    static boolean success = false;

    @Test
    @Order(1)
    void testInterruptCount() {
        provideCondition().forEach(args -> testInterruptCount(
                (Integer) args.get()[0],
                (Integer) args.get()[1],
                (Integer) args.get()[2]));
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

    void testInterruptCount(long waitingTime, long callCount, long callInterval) {
        Waiter waiter = new Waiter(waitingTime);

        assertDoesNotThrow(() -> {
            Thread thread = new Thread(waiter);
            thread.start();
            await().atMost(waitingTime * 2, TimeUnit.MILLISECONDS)
                    .until(() -> {

                        long startTimestamp = System.currentTimeMillis();
                        long callTimestamp = startTimestamp + callInterval;
                        for (int i = 0; i < callCount; i++) {
                            Thread.sleep(callTimestamp - System.currentTimeMillis());
                            thread.interrupt();
                            callTimestamp += callInterval;
                        }

                        thread.join();

                        return true;
                    });
            assertEquals(callCount, waiter.getCount());
        });

    }

    public static Stream<Arguments> provideCondition() {
        return Stream.of(
                Arguments.of(2000, 10, 100),
                Arguments.of(5500, 250, 20),
                Arguments.of(11000, 1000, 10));
    }
}
