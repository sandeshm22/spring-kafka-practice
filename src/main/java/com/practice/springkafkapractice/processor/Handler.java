package com.practice.springkafkapractice.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.Stores;
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
        var store = Stores.persistentTimestampedWindowStore(
                "some-state-store",
                Duration.ofMinutes(1),
                Duration.ofMinutes(1),
                false);
        var materialized = Materialized
                .<String, Long>as(store)
                .withKeySerde(Serdes.String());
        return (consumeMessageStream) -> consumeMessageStream
                .groupBy((key, value) -> getValue(value))
                //.windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofHours(1)))
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofMinutes(10)))
                .count(Named.as("count"), materialized)
                .suppress(Suppressed.untilWindowCloses(unbounded()))
                .toStream()
                .foreach((k, v) -> System.out.printf("Key %s Count is - %s%n", k.key(), v));
    }

    private String getValue(String value) {
        try {
            Map dropfile = mapper.readValue(value, Map.class);
            return (String) dropfile.get("Client");
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
