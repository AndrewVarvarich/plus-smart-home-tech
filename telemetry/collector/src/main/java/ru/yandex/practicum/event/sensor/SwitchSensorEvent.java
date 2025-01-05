package ru.yandex.practicum.event.sensor;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.model.SensorEvent;
import ru.yandex.practicum.types.SensorEventType;

@Getter
@Setter
public class SwitchSensorEvent extends SensorEvent {

    private boolean state;

    @Override
    public SensorEventType getType() {
        return SensorEventType.SWITCH_SENSOR_EVENT;
    }
}
