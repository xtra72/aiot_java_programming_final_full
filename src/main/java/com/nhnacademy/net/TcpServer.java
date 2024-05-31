package com.nhnacademy.net;

import java.net.ServerSocket;
import java.net.Socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TcpServer implements Runnable {
    public static final int DEFAULT_PORT = 1234;
    public static final int MAX_THREAD_COUNT = 5;
    public static final long SHOTDOWN_TIMEOUT = 5000;

    static Logger logger = LogManager.getLogger(TcpServer.class);
    Thread thread;
    int port;
    boolean ready = false;

    ServerSocket linkServerSocket;
    Function<Socket, Runnable> createSession = EchoSession::new;
    ExecutorService executorService;

    public TcpServer() {
        this(DEFAULT_PORT);
    }

    public TcpServer(int port) {
        this.port = port;
        executorService = Executors.newFixedThreadPool(MAX_THREAD_COUNT);
    }

    public void setCreateSession(Function<Socket, Runnable> callback) {
        createSession = callback;
    }

    public synchronized void start() {
        new Thread(this).start();
    }

    public synchronized void stop() {
        if (thread != null) {
            if (linkServerSocket != null) {
                try {
                    linkServerSocket.close();
                } catch (IOException ignore) {
                }
            }
            executorService.shutdownNow();
            try {
                executorService.awaitTermination(SHOTDOWN_TIMEOUT, TimeUnit.MICROSECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean isReady() {
        return ready;
    }

    public boolean isRunning() {
        return thread != null;
    }

    @Override
    public void run() {
        thread = Thread.currentThread();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            linkServerSocket = serverSocket;
            while (!serverSocket.isClosed()) {
                ready = true;
                Socket socket = serverSocket.accept();
                Runnable session = createSession.apply(socket);
                executorService.submit(session);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            ready = false;
            linkServerSocket = null;
        }

        thread = null;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 1234;

        TcpServer server = new TcpServer(port);

        server.start();

        try {
            Thread.sleep(1000);

            for (int i = 0; i < 10; i++) {
                try (Socket socket = new Socket("localhost", port)) {

                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    String message = "hello";

                    System.out.println("Send message : " + message);
                    output.write(message + "\r\n");
                    output.flush();
                    String receivedMessage = input.readLine();
                    System.out.println("Receive Message : " + receivedMessage);
                }
            }
        } finally {
            server.stop();
        }
    }
}
