package ru.yandex.practicum.starter;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.KafkaListener;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

@Component
public class AnalyzerStarter implements ApplicationRunner {

    private final KafkaListener<HubEventAvro> hubEventListener;
    private final KafkaListener<SensorsSnapshotAvro> snapshotListener;

    public AnalyzerStarter(final KafkaListener<HubEventAvro> hubEventListener,
            final KafkaListener<SensorsSnapshotAvro> snapshotListener) {
        this.hubEventListener = hubEventListener;
        this.snapshotListener = snapshotListener;
    }

    @Override
    public void run(final ApplicationArguments args) {
        hubEventListener.start();
        snapshotListener.start();
    }
}