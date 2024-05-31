package com.nhnacademy.net;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class EchoSession implements Runnable {
    Socket socket;

    public EchoSession(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Start Session");
        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            while (socket.isConnected()) {
                String line = input.readLine();
                if (line == null) {
                    break;
                }
                System.out.println("Received : " + line);
                output.write(line + "\r\n");
                output.flush();
            }
        } catch (IOException ignore) {
        } finally {
            try {
                socket.close();
                System.out.println("Session closed");
            } catch (IOException ignore) {
            }
        }
    }
}
