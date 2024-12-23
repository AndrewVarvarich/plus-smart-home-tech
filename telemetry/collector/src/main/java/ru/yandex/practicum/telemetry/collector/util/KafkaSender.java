package ru.yandex.practicum.telemetry.collector.util;

import org.apache.avro.specific.SpecificRecordBase;

public interface KafkaSender {

    void send(String topic, String Key, Long timestamp, SpecificRecordBase message);
}