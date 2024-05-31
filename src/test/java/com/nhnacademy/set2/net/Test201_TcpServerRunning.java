package com.nhnacademy.set2.net;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nhnacademy.net.TcpServer;

@TestMethodOrder(OrderAnnotation.class)
public class Test201_TcpServerRunning {
    static boolean success1 = false;
    static boolean success2 = false;

    @Test
    @Order(1)
    void testInitPassiveRunning() {
        assertDoesNotThrow(() -> {
            TcpServer server = new TcpServer(1234);
            new Thread(server).start();

            await().atMost(1, TimeUnit.SECONDS).until(() -> server.isRunning());

            server.stop();
            await().atMost(1, TimeUnit.SECONDS).until(() -> !server.isRunning());

            success1 = true;
        });
    }

    @Test
    @Order(2)
    void testInitActiveRunning() {
        assertDoesNotThrow(() -> {
            TcpServer server = new TcpServer(1234);

            server.start();

            await().atMost(1, TimeUnit.SECONDS).until(() -> server.isRunning());

            server.stop();
            await().atMost(1, TimeUnit.SECONDS).until(() -> !server.isRunning());

            success2 = true;
        });
    }

    @Test
    @Order(3)
    void testInitPassiveRunning2() {
        assertTrue(success1);
    }

    @ParameterizedTest
    @MethodSource("provideResult")
    @Order(4)
    void testInitActiveRunning2(boolean dummy) {
        assertTrue(success2);
    }

    static Stream<Arguments> provideResult() {
        return Stream.of(
                Arguments.of(success2),
                Arguments.of(success2));
    }
}
