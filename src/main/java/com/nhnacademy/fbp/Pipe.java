package com.nhnacademy.fbp;

import java.util.Deque;
import java.util.LinkedList;

import com.fasterxml.jackson.databind.JsonNode;

public class Pipe {
    Deque<JsonNode> messageQueue = new LinkedList<>();
    int maxCount = 0;

    public boolean isEmpty() {
        return messageQueue.isEmpty();
    }

    public int getCount() {
        return messageQueue.size();
    }

    public synchronized void push(JsonNode message) {
        try {
            while ((maxCount != 0) && (maxCount <= messageQueue.size())) {
                wait();
            }

            messageQueue.push(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized JsonNode poll() {
        JsonNode message = messageQueue.pollFirst();

        notifyAll();

        return message;
    }
}
