package ru.yandex.practicum.telemetry.collector.util;

public interface KafkaClient {

    KafkaSender getSender();
}