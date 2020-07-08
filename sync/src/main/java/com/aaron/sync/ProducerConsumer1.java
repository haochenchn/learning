package com.aaron.sync;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Aaron
 * @description synchronized实现：生产者消费者
 * @date 2020/7/8
 */
public class ProducerConsumer1<T> {
    private final Queue<T> queue = new LinkedList<>();
    private final int MAX = 10; // 最多10个元素

    public synchronized void put(T t) {
        while (queue.size() == MAX) {
            try {
                System.out.println("---------------仓库满了");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        queue.add(t);
        System.out.println("生产了产品：" + t.toString());
        this.notifyAll();
    }

    public synchronized T get(){

        while (queue.size() == 0) {
            try {
                System.out.println("---------------缺货");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        T obj = queue.poll();
        System.out.println("消费了产品：" + obj.toString());
        this.notifyAll();
        return obj;
    }

    public static void main(String[] args) {
        ProducerConsumer1<String> myContainer1 = new ProducerConsumer1<>();
        // 启动消费者线程
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("消费者--"+myContainer1.get());
                }
            }, "消费者-" + i).start();
        }


        // 启动生产者线程
        for (int i = 1; i < 3; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(200));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    myContainer1.put(Thread.currentThread().getName() + "" + j);
                }
            }, "产品-" + i).start();
        }
    }

}
