package com.nhnacademy.thread;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Counter implements Runnable {
    static AtomicInteger totalCount = new AtomicInteger();
    static final Map<Long, Integer> storage = new HashMap<>();

    /**
     * 실행 후 임의의 그룹 카운터를 하나 증가 시킨다.
     */
    @Override
    public void run() {
        int count = 0;

        if (storage.containsKey(Thread.currentThread().getId())) {
            count = storage.get(Thread.currentThread().getId());
        } else {
            System.out.println("Thread : " + Thread.currentThread().getId());
        }

        Counter.totalCount.incrementAndGet();
        count++;

        storage.put(Thread.currentThread().getId(), count);
    }

    /**
     * 카운터 그룹을 삭제한다.
     */
    public static void clear() {
        storage.clear();
        totalCount.set(0);
    }

    /**
     * Counter가 실행된 총 횟수를 반환한다.
     * clear 호출시 초기화 된다.
     * 
     * @return 횟수
     */
    public static int getTotalCount() {
        return totalCount.intValue();
    }

    /*
     * 실행된 카운터 결과를 반환한다.
     * Thread pool의 갯수에 해당하는 만큼의 카운터 그룹이 생성되고, 총합은 실행된 카운터와 동일해야 한다.
     *
     * @return 카운터 그룹
     */
    public static int[] getCounts() {
        return storage.values().stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 20; i++) {
            executor.submit(new Counter());
        }

        executor.shutdown();

        if (executor.awaitTermination(1, TimeUnit.SECONDS)) {
            System.out.println(LocalTime.now() + " - 모든 작업이 완료되었습니다.");
        }
    }
}
