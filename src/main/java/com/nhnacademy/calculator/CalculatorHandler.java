package com.nhnacademy.calculator;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.number.XNumber;

public class CalculatorHandler implements Runnable {
    Calculator calculator = new Calculator();
    ObjectMapper mapper = new ObjectMapper();
    Socket socket;

    public CalculatorHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Started");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            while (socket.isConnected()) {
                String message = reader.readLine();

                System.out.println("Received : " + message);
                if (message.length() != 0) {
                    writer.write(calculation(message));
                    writer.write("\r\n");
                    writer.flush();
                }
            }
        } catch (IOException e) {
        }
    }

    String calculation(String expression) {
        try {
            JsonNode node = mapper.readTree(expression);

            System.out.println("Node = " + node.toString());
            XNumber result = calculator.evaluate(node.get("expr").asText());

            return String.format("{\"result\": \"%s\"}", result.toString());
        } catch (JacksonException e) {
            return "{\"result\": \"error\"}";
        }
    }
}
