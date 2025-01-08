package ru.yandex.practicum.processor;

import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

public interface HubEventProcessor {

    Class<? extends SpecificRecordBase> getPayloadType();

    void handle(HubEventAvro event);
}