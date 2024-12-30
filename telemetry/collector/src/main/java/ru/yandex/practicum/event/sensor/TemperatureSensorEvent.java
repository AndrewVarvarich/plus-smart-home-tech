package ru.yandex.practicum.event.sensor;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.model.SensorEvent;
import ru.yandex.practicum.types.SensorEventType;

@Getter
@Setter
public class TemperatureSensorEvent extends SensorEvent {

    private Integer temperatureC;
    private Integer temperatureF;

    @Override
    public SensorEventType getType() {
        return SensorEventType.TEMPERATURE_SENSOR_EVENT;
    }
}
