package com.practice.springkafkapractice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Duration;
import java.util.Map;
import java.util.function.Consumer;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class SpringKafkaPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaPracticeApplication.class, args);
    }
/*
    @Bean
    public Function<KStream<String, String>, KStream<String, String>> processmessage() {
        return processedMessage -> {
            return processedMessage.map((key, value) -> {
                System.out.println("CONSUMED " + key + "-" + value);
                return KeyValue.pair(UUID.randomUUID().toString(), value);
            });
        };
    }*/
/*
    @Bean
    public BiFunction<KStream<String, String>, KStream<String, String>, KStream<String, String>> processMessage() {
        return (processedMessage, processedMessage1) ->
                processedMessage.join(processedMessage1, (V1, V2) -> V1 + "-" + V2, JoinWindows.of(Duration.ofSeconds(10L))).peek((k, v) -> log.info("processed - " + v));
    }
 */

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }



}


