package com.nhnacademy.set2.net;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.concurrent.TimeUnit;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.nhnacademy.net.TcpServer;

@TestMethodOrder(OrderAnnotation.class)
public class Test203_MultiConnectionServer {
    static int multiConnectionEchoServicePort = 1237;
    static TcpServer multiConnectionEchoServer;

    @BeforeAll
    @Order(1)
    static void initMultiConnectionEchoServer() {
        multiConnectionEchoServer = new TcpServer(multiConnectionEchoServicePort);
        multiConnectionEchoServer.start();
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4, 5 })
    @Order(2)
    void testMultiConnectionEchoServer(int maxLoopCount) {
        assertDoesNotThrow(() -> {

            try {
                await().atMost(1, TimeUnit.SECONDS).until(() -> multiConnectionEchoServer.isRunning());
                for (int loop = 0; loop < maxLoopCount; loop++) {
                    try (Socket socket = new Socket("localhost", multiConnectionEchoServicePort)) {

                        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                        String message = "hello";

                        output.write(message);
                        output.write("\r\n");
                        output.flush();
                        await().atMost(1, TimeUnit.SECONDS).until(() -> {
                            String receivedMessage = input.readLine();

                            return message.equals(receivedMessage);
                        });
                    }
                }
            } finally {
            }
        });
    }

    @AfterAll
    @Order(3)
    static void finaltMultiConnectionEchoServer() {
        multiConnectionEchoServer.stop();
    }
}
