package ru.yandex.practicum.extractor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.ConditionTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;

@Component
public class SwitchSensorSwitchExtractor extends BaseSwitchSensorValueExtractor {

    @Override
    public ConditionTypeAvro getMetricType() {
        return ConditionTypeAvro.SWITCH;
    }

    @Override
    protected Object extractValue(final SwitchSensorAvro data) {
        return data.getState();
    }
}