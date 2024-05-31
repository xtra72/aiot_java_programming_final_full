package com.nhnacademy.set2.fbp;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.nhnacademy.fbp.Node;

@TestMethodOrder(OrderAnnotation.class)
public class Test302_Node {
    static boolean success = false;

    class DummyNode extends Node {
        public int processCallCount = 0;
        public int preprocessCallCount = 0;
        public int postprocessCallCount = 0;

        @Override
        public void preprocess() {
            super.preprocess();
            ++preprocessCallCount;
        }

        @Override
        public void postprocess() {
            super.postprocess();
            ++postprocessCallCount;
        }

        @Override
        public void process() {
            processCallCount++;
        }
    }

    @Test
    @Order(1)
    void testInterval() {
        DummyNode node = new DummyNode();
        int newInterval = 200;

        assertEquals(Node.DEFAULT_INTERVAL, node.getInterval());
        node.setInterval(newInterval);
        assertEquals(newInterval, node.getInterval());

        Thread thread = new Thread(node);

        thread.start();
        await().atLeast(1, TimeUnit.SECONDS)
                .atMost(2, TimeUnit.SECONDS)
                .until(() -> node.processCallCount > 6);

        assertTrue(node.preprocessCallCount == 1 && node.postprocessCallCount == 0);
        node.stop();

        assertDoesNotThrow(() -> thread.join(1000));

        assertTrue(node.preprocessCallCount == 1 && node.postprocessCallCount == 1);

        success = true;
    }

    @ParameterizedTest
    @MethodSource("provideResult")
    @Order(2)
    void testInterval2(boolean dummy) {
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
                Arguments.of(success));
    }
}
