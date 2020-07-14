package com.aaron.sync;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Aaron
 * @description BlockingQueue实现：生产者消费者
 * @date 2020/7/8
 */
public class ProducerConsumer3 {
    private BlockingQueue<String> queue = new LinkedBlockingQueue<>(10);

}
