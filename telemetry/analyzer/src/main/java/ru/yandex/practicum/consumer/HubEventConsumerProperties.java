package ru.yandex.practicum.consumer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.yandex.practicum.kafka.KafkaListenerProperties;

@ConfigurationProperties("kafka.hub-event-listener")
public class HubEventConsumerProperties extends KafkaListenerProperties {

}