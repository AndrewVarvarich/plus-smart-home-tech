package ru.yandex.practicum.service.grpc;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.MotionSensorProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

@Component
public class MotionSensorEventHandler implements SensorEventHandler {
    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.MOTION_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto event) {
        if (event.hasMotionSensorEvent()) {
            MotionSensorProto motionEvent = event.getMotionSensorEvent();
            System.out.println("Обработано событие переключателя: motion=" + motionEvent.getMotion());
        } else {
            System.out.println("Получено неизвестное событие.");
        }
    }
}