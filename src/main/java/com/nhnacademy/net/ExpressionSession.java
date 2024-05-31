package com.nhnacademy.net;

import java.net.Socket;

import com.nhnacademy.calculator.Calculator;
import com.nhnacademy.number.XNumber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ExpressionSession implements Runnable {
    Calculator calculator = new Calculator();
    Socket socket;

    public ExpressionSession(Socket socket) {
        this.socket = socket;
    }

    String evaluate(BufferedReader reader) throws IOException {
        String expression = reader.readLine();

        XNumber result = calculator.evaluate(expression);

        return result.toString();
    }

    @Override
    public void run() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream())) {
            while (socket.isConnected()) {
                output.println(evaluate(input));
                output.flush();
            }
        } catch (IOException ignore) {

        } finally {
            try {
                socket.close();
            } catch (IOException ignore) {
            }
        }
    }
}
