package com.nhnacademy.calculator;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CalculatorClient {
    ObjectMapper mapper = new ObjectMapper();
    String host;
    int port;
    Socket socket;

    public CalculatorClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
    }

    public void close() throws IOException {
        socket.close();
        socket = null;
    }

    public String evaluate(String expression) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        ObjectNode objectNode = mapper.getNodeFactory().objectNode();
        objectNode.put("expr", expression);

        writer.write(objectNode.toString());
        writer.write("\r\n");
        writer.flush();

        return reader.readLine();
    }
}
