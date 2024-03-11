package com.practice.springkafkapractice.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.state.Stores;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class Handler {

    private final ObjectMapper mapper;

    public Handler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Bean
    public Consumer<KStream<String, String>> consumeMessage() {
        var store = Stores.persistentTimestampedWindowStore(
                "count-of-operations",
                Duration.ofMinutes(1),
                Duration.ofMinutes(1),
                false);
        var materialized = Materialized
                .<String, Long>as(store)
                .withKeySerde(Serdes.String());
        return (consumeMessageStream) -> consumeMessageStream
                .groupBy((key, value) -> getValue(value))
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofMinutes(1)))
                .count(Named.as("count"), materialized);
    }

    private String getValue(String value) {
        try {
            Map operation = mapper.readValue(value, Map.class);
            return (String) operation.get("Client");
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
