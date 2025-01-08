package ru.yandex.practicum.processor;

import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

public interface SnapshotProcessor {

    void handle(SensorsSnapshotAvro snapshot);
}