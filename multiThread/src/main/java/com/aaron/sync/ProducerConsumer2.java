package com.aaron.sync;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Aaron
 * @description ReentrantLock实现：生产者消费者
 * @date 2020/7/8
 */
public class ProducerConsumer2<T> {
    private final Queue<T> queue = new LinkedList<>();
    private final int MAX = 10; // 最多10个元素

    private Lock lock = new ReentrantLock();
    Condition producer = lock.newCondition();
    Condition consumer = lock.newCondition();

    public void put(T t) {
        try {
            lock.lock();
            while(queue.size() == MAX) {
                try {
                    System.out.println("------------------当前队列满");
                    producer.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.add(t);
            System.out.println("生产了产品：" + t.toString());
            consumer.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public T get() {
        T obj = null;
        try{
            lock.lock();
            while (queue.size() == 0) {
                try {
                    System.out.println("-----------------当前队列为空");
                    consumer.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            obj = queue.poll();
            System.out.println("消费了产品：" + obj.toString());
            producer.signalAll();
        } finally {
            lock.unlock();
        }
        return obj;
    }

    public static void main(String[] args) {
        ProducerConsumer2<String> myContainer2 = new ProducerConsumer2<String>();

        // 启消费者线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 2; j++) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("消费者--"+myContainer2.get());
                }
            }, "consumer_" + i).start();
        }

        // 启动生产者线程
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(200));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    myContainer2.put(Thread.currentThread().getName() + "" + j);
                }
            }, "产品-" + i).start();
        }
    }

}
