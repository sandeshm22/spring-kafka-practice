package com.practice.springkafkapractice;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class KafkaProducerConsumer {

    String[] data = new String[]{"{\"Client\": \"Operation0\"}", "{\"Client\": \"Operation1\"}", "{\"Client\": \"Operation2\"}", "{\"Client\": \"Operation3\"}", "{\"Client\": \"Operation4\"}"};

    private final KafkaTemplate<String, String> template;

    public KafkaProducerConsumer(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    @Scheduled(cron = "*/2 * * * * *")
    public void sendMessage() {
        sendData();
        sendData();
        sendData();
        sendData();
        sendData();
        sendData();
        sendData();
        sendData();
        sendData();
        sendData();
        sendData();
        sendData();
        sendData();
        sendData();
        sendData();
        sendData();
        sendData();
        sendData();
        sendData();
        sendData();
        sendData();
        sendData();
        sendData();
        sendData();
        sendData();
        sendData();
    }

    private void sendData() {
        String correlationId = UUID.randomUUID().toString();
        ProducerRecord<String, String> requestFile = new ProducerRecord<>("request1", correlationId,  data[0]);
        ProducerRecord<String, String> requestFile11 = new ProducerRecord<>("request1", correlationId,  data[0]);
        ProducerRecord<String, String> requestFile1= new ProducerRecord<>("request1", UUID.randomUUID().toString(), data[1]);
        ProducerRecord<String, String> requestFile2 = new ProducerRecord<>("request1", UUID.randomUUID().toString(), data[2]);
        ProducerRecord<String, String> requestFile3 = new ProducerRecord<>("request1", UUID.randomUUID().toString(), data[3]);
        ProducerRecord<String, String> requestFile4 = new ProducerRecord<>("request1", UUID.randomUUID().toString(), data[4]);
        template.send(requestFile);
        template.send(requestFile1);
        template.send(requestFile11);
        template.send(requestFile2);
        template.send(requestFile3);
        template.send(requestFile4);
    }


 /*   @KafkaListener(topics = {"testprocess.combined"}, groupId = "test-combined")
    public void consumeMessagesss(ConsumerRecord<String, String> message) {
        System.out.println(" MESSAGE " + message.key() + " " + message.value());
    }*/
}
