package com.nhnacademy.fbp;

public abstract class Node implements Runnable {
    public static final int DEFAULT_INTERVAL = 100;
    Thread currentThread;
    boolean stopped = true;
    long interval = DEFAULT_INTERVAL;

    protected Node() {
    }

    public void setInterval(long interval) {
        if (interval < 0) {
            throw new IllegalArgumentException();
        }

        this.interval = interval;
    }

    public long getInterval() {
        return interval;
    }

    public boolean isRunning() {
        return !stopped;
    }

    public synchronized void stop() {
        if (!stopped) {
            stopped = true;
            if ((currentThread != null) && (currentThread != Thread.currentThread())) {
                currentThread.interrupt();
            }
        }
    }

    public void preprocess() {
    }

    public void postprocess() {

    }

    public abstract void process();

    public void run() {
        currentThread = Thread.currentThread();
        stopped = false;

        preprocess();

        while (!stopped) {
            try {
                process();
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                stopped = true;
                Thread.currentThread().interrupt();
            }
        }

        postprocess();
    }
}
