package ru.yandex.practicum.consumer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.yandex.practicum.kafka.KafkaListenerProperties;

@ConfigurationProperties("kafka.snapshot-listener")
public class SnapshotListenerProperties extends KafkaListenerProperties {

}