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

import com.nhnacademy.net.ExpressionSession;
import com.nhnacademy.net.TcpServer;

public class Test205_CalculatorServer {
    @ParameterizedTest
    @MethodSource("provideExpression")
    void testCalculatorServer(String expression, String result) {
        int port = 1239;

        assertDoesNotThrow(() -> {
            TcpServer server = new TcpServer(port);
            server.setCreateSession(ExpressionSession::new);

            server.start();

            try {
                try (Socket socket = new Socket("localhost", port)) {

                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    output.write(expression);
                    output.write("\r\n");
                    output.flush();
                    await().atMost(2, TimeUnit.SECONDS).until(() -> {
                        String receivedMessage = input.readLine();
                        return receivedMessage.equals(result);
                    });
                }
            } finally {
                server.stop();
            }
        });
    }

    static Stream<Arguments> provideExpression() {
        return Stream.of(
                Arguments.of("1", "3"),
                Arguments.of("1+2", "3"),
                Arguments.of("1+2*3", "7"),
                Arguments.of("1+2*-3", "-5"),
                Arguments.of("1+2*[2,3]", "[7,3]"),
                Arguments.of("[1,2]+[1,2]*[2,3]", "[5,6]"),
                Arguments.of("[1,2]+[1,2]*[-2,3]", "[1,6]"),
                Arguments.of("[1,2]*[1,2]*[-2,3]", "[-1,6]"),
                Arguments.of("-3 --2 + ([-2, 5] * [2,-3] - (7 / [ 4, 5]))", "[-569,60]"),
                Arguments.of("-3 * 2 + [23, 5] * [2,3] - (2 / [11,3])", "[-574,165]"));
    }
}
