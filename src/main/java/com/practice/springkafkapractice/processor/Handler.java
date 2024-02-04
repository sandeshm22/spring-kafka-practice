package com.practice.springkafkapractice.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Suppressed;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.function.Consumer;

import static org.apache.kafka.streams.kstream.Suppressed.BufferConfig.unbounded;

@Component
public class Handler {

    private final ObjectMapper mapper;

    public Handler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Bean
    public Consumer<KStream<String, String>> consumeMessage() {
        return (consumeMessageStream) -> consumeMessageStream
                .groupBy((key, value) -> getValue(value))
                //.windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofHours(1)))
                .windowedBy(TimeWindows.of(Duration.ofMinutes(10)))
                .count(Named.as("counts-store"))
                .suppress(Suppressed.untilWindowCloses(unbounded()))
                .toStream()
                .foreach((k, v) -> System.out.printf("Key %s Count is - %s%n", k.key(), v));
    }

    private String getValue(String value) {
        try {
            Map<String, String> dropfile = mapper.readValue(value, Map.class);
            return dropfile.get("Client");
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
