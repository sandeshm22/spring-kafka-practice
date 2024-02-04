package com.practice.springkafkapractice;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component

public class KafkaProducerConsumer {

    static Integer i = 0;

    String[] data = new String[]{"{\"TargetOp\": \"Zuul.Operation0\"}", "{\"TargetOp\": \"Zuul.Operation1\"}", "{\"TargetOp\": \"Zuul.Operation2\"}", "{\"TargetOp\": \"Zuul.Operation3\"}", "{\"TargetOp\": \"Zuul.Operation4\"}"};

    private KafkaTemplate<String, Long> template;

    public KafkaProducerConsumer(KafkaTemplate<String, Long> template) {
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

    }

    private void sendData() {
        String correlationId = UUID.randomUUID().toString();
        ProducerRecord requestFile = new ProducerRecord<>("request", correlationId,  data[0]);
        ProducerRecord requestFile1= new ProducerRecord<>("request", UUID.randomUUID().toString(), data[1]);
        ProducerRecord requestFile2 = new ProducerRecord<>("request", UUID.randomUUID().toString(), data[2]);
        ProducerRecord requestFile3 = new ProducerRecord<>("request", UUID.randomUUID().toString(), data[3]);
        ProducerRecord requestFile4 = new ProducerRecord<>("request", UUID.randomUUID().toString(), data[4]);
        template.send(requestFile);
        template.send(requestFile1);
        template.send(requestFile2);
        template.send(requestFile3);
        template.send(requestFile4);
    }

    private String getRequest(int i) throws IOException {
        return data[i];
    }


 /*   @KafkaListener(topics = {"testprocess.combined"}, groupId = "test-combined")
    public void consumeMessagesss(ConsumerRecord<String, String> message) {
        System.out.println(" MESSAGE " + message.key() + " " + message.value());
    }*/
}
