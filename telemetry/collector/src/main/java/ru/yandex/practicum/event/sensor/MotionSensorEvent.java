package ru.yandex.practicum.event.sensor;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.model.SensorEvent;
import ru.yandex.practicum.types.SensorEventType;

@Getter
@Setter
public class MotionSensorEvent extends SensorEvent {

    private Integer linkQuality;
    private boolean motion;
    private Integer voltage;

    @Override
    public SensorEventType getType() {
        return SensorEventType.MOTION_SENSOR_EVENT;
    }
}
