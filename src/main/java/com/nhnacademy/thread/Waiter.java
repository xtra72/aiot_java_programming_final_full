package com.nhnacademy.thread;

public class Waiter implements Runnable {
    Thread thread;
    int count = 0;
    boolean running = false;
    long waitingTime;

    public Waiter(long waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getCount() {
        return count;
    }

    public void stop() {
        if (running) {
            running = false;
            thread.interrupt();
        }

    }

    public void join() throws InterruptedException {
        thread.join();
    }

    @Override
    public void run() {

        thread = Thread.currentThread();
        running = true;
        long startTimestamp = System.currentTimeMillis();
        long wakeupTimestamp = startTimestamp + waitingTime;

        while (running) {
            try {
                long currentTimestamp = System.currentTimeMillis();
                if (wakeupTimestamp <= currentTimestamp) {
                    break;
                }
                Thread.sleep(wakeupTimestamp - currentTimestamp);
            } catch (InterruptedException e) {
                count++;
                Thread.currentThread().interrupt();
                Thread.interrupted();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Waiter waiter = new Waiter(5500);

        long startTimestamp = System.currentTimeMillis();
        Thread thread = new Thread(waiter);

        thread.start();

        for (int i = 0; i < 500; i++) {
            Thread.sleep(10);
            System.out.println("Wakeup!");
            thread.interrupt();
        }

        waiter.join();
        long finishTimestamp = System.currentTimeMillis();

        System.out.println("Count : " + waiter.getCount() + ", Elapsed Time : " + (finishTimestamp - startTimestamp));
    }
}
