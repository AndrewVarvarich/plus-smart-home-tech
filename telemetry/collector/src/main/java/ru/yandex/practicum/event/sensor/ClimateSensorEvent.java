package ru.yandex.practicum.event.sensor;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.model.SensorEvent;
import ru.yandex.practicum.types.SensorEventType;

@Getter
@Setter
public class ClimateSensorEvent extends SensorEvent {

    private Integer temperatureC;

    private Integer humidity;

    private Integer co2Level;

    @Override
    public SensorEventType getType() {
        return SensorEventType.CLIMATE_SENSOR_EVENT;
    }
}
