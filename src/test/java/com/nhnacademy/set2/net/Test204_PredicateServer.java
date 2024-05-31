package com.nhnacademy.set2.net;

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

import com.nhnacademy.net.PredicateSession;
import com.nhnacademy.net.TcpServer;

public class Test204_PredicateServer {
    @ParameterizedTest
    @MethodSource("providePredicate")
    void testPredicateServer() {
        int port = 1238;

        assertDoesNotThrow(() -> {
            TcpServer server = new TcpServer(port);

            try {
                server.setCreateSession(PredicateSession::new);

                server.start();

                try (Socket socket = new Socket("localhost", port)) {

                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    String expression = "1";

                    output.write(expression);
                    output.write("\r\n");
                    output.flush();
                    await().atMost(2, TimeUnit.SECONDS).until(() -> {
                        String receivedMessage = input.readLine();
                        return receivedMessage.equals("false");
                    });
                }
            } finally {
                server.stop();
            }

        });
    }

    static Stream<Arguments> providePredicate() {
        return Stream.of(
                Arguments.of("1", "false"),
                Arguments.of("2", "true"),
                Arguments.of("3", "false"),
                Arguments.of("4", "true"),
                Arguments.of("5", "false"));
    }
}
