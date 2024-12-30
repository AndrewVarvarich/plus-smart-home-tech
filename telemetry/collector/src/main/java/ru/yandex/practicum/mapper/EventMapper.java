package ru.yandex.practicum.mapper;

import ru.yandex.practicum.event.hubs.DeviceAddedEvent;
import ru.yandex.practicum.event.hubs.DeviceRemovedEvent;
import ru.yandex.practicum.event.scenario.DeviceAction;
import ru.yandex.practicum.event.scenario.ScenarioAddedEvent;
import ru.yandex.practicum.event.scenario.ScenarioCondition;
import ru.yandex.practicum.event.scenario.ScenarioRemovedEvent;
import ru.yandex.practicum.event.sensor.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

public interface EventMapper {

    public ClimateSensorAvro climateSensorAvro(ClimateSensorEvent climateSensorEvent);

    public LightSensorAvro lightSensorAvro(LightSensorEvent lightSensorEvent);

    public MotionSensorAvro motionSensorAvro(MotionSensorEvent motionSensorEvent);

    public SwitchSensorAvro switchSensorAvro(SwitchSensorEvent switchSensorEvent);

    public TemperatureSensorAvro temperatureSensorAvro(TemperatureSensorEvent temperatureSensorEvent);

    public ScenarioConditionAvro scenarioConditionAvro(ScenarioCondition scenarioCondition);

    public DeviceActionAvro deviceActionAvro(DeviceAction deviceAction);

    public HubEventAvro hubEventAvro(ScenarioAddedEvent scenarioAddedEvent);

    public HubEventAvro hubEventAvroAdded(DeviceAddedEvent deviceAddedEvent);

    public HubEventAvro hubEventAvroRemoved(DeviceRemovedEvent deviceRemovedEvent);
}
