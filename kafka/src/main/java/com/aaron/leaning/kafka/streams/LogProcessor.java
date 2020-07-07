package com.aaron.leaning.kafka.streams;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;

/**
 * @author Aaron
 * @description 具体业务处理
 * @date 2020/7/8
 */
public class LogProcessor implements Processor<byte[], byte[]> {

    private ProcessorContext context;

    @Override
    public void init(ProcessorContext processorContext) {
        this.context = context;
    }

    @Override
    public void process(byte[] key, byte[] value) {
        String input = new String(value);

        // 如果包含“>>>”则只保留该标记后面的内容
        if (input.contains(">>>")) {
            input = input.split(">>>")[1].trim();
            // 输出到下一个topic
            context.forward("logProcessor".getBytes(), input.getBytes());
        }else{
            context.forward("logProcessor".getBytes(), input.getBytes());
        }

    }

    @Override
    public void punctuate(long l) {

    }

    @Override
    public void close() {

    }
}
