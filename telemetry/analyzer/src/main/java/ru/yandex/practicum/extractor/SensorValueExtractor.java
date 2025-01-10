package ru.yandex.practicum.extractor;

import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.kafka.telemetry.event.ConditionTypeAvro;

public interface SensorValueExtractor {

    Class<? extends SpecificRecordBase> getDataType();

    ConditionTypeAvro getMetricType();

    Object extractValue(Object data);
}