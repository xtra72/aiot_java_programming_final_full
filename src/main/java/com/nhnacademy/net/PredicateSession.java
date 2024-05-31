package com.nhnacademy.net;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class PredicateSession implements Runnable {
    Socket socket;

    public PredicateSession(Socket socket) {
        this.socket = socket;
    }

    int readValue(BufferedReader reader) throws IOException {
        return Integer.valueOf(reader.readLine());
    }

    @Override
    public void run() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream())) {
            while (socket.isConnected()) {
                output.println((readValue(input) % 2 == 0) ? "true" : "false");
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
