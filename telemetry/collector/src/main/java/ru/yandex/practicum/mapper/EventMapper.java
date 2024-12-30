package ru.yandex.practicum.mapper;

import ru.yandex.practicum.event.hubs.DeviceAddedEvent;
import ru.yandex.practicum.event.hubs.DeviceRemovedEvent;
import ru.yandex.practicum.event.scenario.DeviceAction;
import ru.yandex.practicum.event.scenario.ScenarioAddedEvent;
import ru.yandex.practicum.event.scenario.ScenarioCondition;
import ru.yandex.practicum.event.sensor.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

public interface EventMapper {

    ClimateSensorAvro climateSensorAvro(ClimateSensorEvent climateSensorEvent);

    LightSensorAvro lightSensorAvro(LightSensorEvent lightSensorEvent);

    MotionSensorAvro motionSensorAvro(MotionSensorEvent motionSensorEvent);

    SwitchSensorAvro switchSensorAvro(SwitchSensorEvent switchSensorEvent);

    TemperatureSensorAvro temperatureSensorAvro(TemperatureSensorEvent temperatureSensorEvent);

    ScenarioConditionAvro scenarioConditionAvro(ScenarioCondition scenarioCondition);

    DeviceActionAvro deviceActionAvro(DeviceAction deviceAction);

    HubEventAvro hubEventAvro(ScenarioAddedEvent scenarioAddedEvent);

    HubEventAvro hubEventAvroAdded(DeviceAddedEvent deviceAddedEvent);

    HubEventAvro hubEventAvroRemoved(DeviceRemovedEvent deviceRemovedEvent);
}
