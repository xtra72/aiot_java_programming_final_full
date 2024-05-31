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
import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nhnacademy.net.TcpServer;

@TestMethodOrder(OrderAnnotation.class)
public class Test202_EchoServer {
    static boolean success = false;

    @Test
    @Order(1)
    void testEchoServer() {
        int port = 1236;

        assertDoesNotThrow(() -> {
            TcpServer server = new TcpServer(port);

            try {
                server.start();

                await().atMost(1, TimeUnit.SECONDS).until(() -> server.isReady());

                try (Socket socket = new Socket("localhost", port)) {

                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    String message = "hello";

                    output.write(message);
                    output.write("\r\n");
                    output.flush();
                    await().atMost(2, TimeUnit.SECONDS).until(() -> {
                        String receivedMessage = input.readLine();

                        return message.equals(receivedMessage);
                    });
                }
            } finally {
                server.stop();
            }

            success = true;
        });
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