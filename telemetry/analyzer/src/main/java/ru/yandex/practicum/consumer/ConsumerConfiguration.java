package ru.yandex.practicum.consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.kafka.KafkaListener;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.dispatcher.HubEventDispatcher;
import ru.yandex.practicum.processor.SnapshotProcessor;

@Configuration
public class ConsumerConfiguration {

    @Bean(destroyMethod = "stop")
    public KafkaListener<HubEventAvro> hubEventListener(final HubEventConsumerProperties config,
                                                        final HubEventDispatcher hubEventDispatcher) {
        return new KafkaListener<>(HubEventAvro.getClassSchema(), config, hubEventDispatcher::dispatch);
    }

    @Bean(destroyMethod = "stop")
    public KafkaListener<SensorsSnapshotAvro> snapshotListener(final SnapshotListenerProperties config,
                                                               final SnapshotProcessor snapshotHandler) {
        return new KafkaListener<>(SensorsSnapshotAvro.getClassSchema(), config, snapshotHandler::handle);
    }
}
