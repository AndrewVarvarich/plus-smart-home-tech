package ru.yandex.practicum.service.grpc;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SwitchSensorProto;

@Component
public class SwitchSensorEventHandler implements SensorEventHandler {

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.SWITCH_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto event) {
        if (event.hasSwitchSensorEvent()) {
            SwitchSensorProto switchEvent = event.getSwitchSensorEvent();
            System.out.println("Обработано событие переключателя: state=" + switchEvent.getState());
        } else {
            System.out.println("Получено неизвестное событие.");
        }
    }
}