package ru.yandex.practicum.telemetry.collector.mapper;


import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.telemetry.collector.dto.hub.DeviceAddedEvent;
import ru.yandex.practicum.telemetry.collector.dto.hub.DeviceRemovedEvent;
import ru.yandex.practicum.telemetry.collector.dto.hub.ScenarioAddedEvent;
import ru.yandex.practicum.telemetry.collector.dto.hub.ScenarioRemovedEvent;
import ru.yandex.practicum.telemetry.collector.dto.sensor.*;

public interface EventMapper {

    SensorEventAvro mapToAvro(ClimateSensorEvent event);

    SensorEventAvro mapToAvro(LightSensorEvent event);

    SensorEventAvro mapToAvro(MotionSensorEvent event);

    SensorEventAvro mapToAvro(SwitchSensorEvent event);

    SensorEventAvro mapToAvro(TemperatureSensorEvent event);

    HubEventAvro mapToAvro(DeviceAddedEvent event);

    HubEventAvro mapToAvro(DeviceRemovedEvent event);

    HubEventAvro mapToAvro(ScenarioAddedEvent event);

    HubEventAvro mapToAvro(ScenarioRemovedEvent event);
}