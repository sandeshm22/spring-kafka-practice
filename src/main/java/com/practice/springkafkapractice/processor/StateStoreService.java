package com.practice.springkafkapractice.processor;

import org.apache.kafka.streams.state.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class StateStoreService {
    @Autowired
    private InteractiveQueryService interactiveQueryService;

    @Scheduled(fixedDelay = 60000)
    public void readFromSchedule() {
        ReadOnlyWindowStore<Object, ValueAndTimestamp<Object>> keyValueStore = interactiveQueryService.getQueryableStore("count-of-operations", QueryableStoreTypes.timestampedWindowStore());
        keyValueStore.all().forEachRemaining(stringLongKeyValue -> System.out.println(stringLongKeyValue.key.toString() + " --  " + stringLongKeyValue.value));
    }
}
