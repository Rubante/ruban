package com.ruban.learning.kafka;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import com.ruban.framework.core.utils.commons.RandomUtil;

public class KafkaTest {

    @Test
    public void testProduce() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.200.15.12:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");

        Producer<byte[], byte[]> producer = new KafkaProducer<>(props);
        
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            startTime = System.currentTimeMillis();
            producer.send(new ProducerRecord<byte[], byte[]>("my-topic-1", Integer.toString(i*100).getBytes(), Integer.toString(i*100).getBytes()));
            System.out.println(""+(System.currentTimeMillis() -startTime));
        }
        
        try{
            Thread.sleep(100000);
            producer.send(new ProducerRecord<byte[], byte[]>("my-topic-1", Integer.toString(100).getBytes(), Integer.toString(100).getBytes()));
        }catch(Exception ex){
            ex.printStackTrace();
        }
        producer.close();
    }

    @Test
    public void testConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.200.15.12:9092");
        props.put("group.id", "test3");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        KafkaConsumer<byte[], byte[]> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("my-topic-1", "my-topic"));

        while (true) {
            ConsumerRecords<byte[], byte[]> records = consumer.poll(1000);
            for (ConsumerRecord<byte[], byte[]> record : records)
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), new String(record.key()), new String(record.value()));
        }
    }
}
