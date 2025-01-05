package ru.yandex.practicum.event.sensor;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.model.SensorEvent;
import ru.yandex.practicum.types.SensorEventType;

@Getter
@Setter
public class LightSensorEvent extends SensorEvent {

    private Integer linkQuality;
    private Integer luminosity;

    @Override
    public SensorEventType getType() {
        return SensorEventType.LIGHT_SENSOR_EVENT;
    }
}
