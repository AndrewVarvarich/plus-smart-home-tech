package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.event.hubs.DeviceAddedEvent;
import ru.yandex.practicum.event.hubs.DeviceRemovedEvent;
import ru.yandex.practicum.event.scenario.DeviceAction;
import ru.yandex.practicum.event.scenario.ScenarioAddedEvent;
import ru.yandex.practicum.event.scenario.ScenarioCondition;
import ru.yandex.practicum.event.sensor.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

@Component
public class EventMapperImpl implements EventMapper {

    public ClimateSensorAvro climateSensorAvro(ClimateSensorEvent climateSensorEvent) {
        return ClimateSensorAvro.newBuilder()
                .setCo2Level(climateSensorEvent.getCo2Level())
                .setHumidity(climateSensorEvent.getHumidity())
                .setTemperatureC(climateSensorEvent.getTemperatureC())
                .build();
    }

    public LightSensorAvro lightSensorAvro(LightSensorEvent lightSensorEvent) {
        return LightSensorAvro.newBuilder()
                .setLinkQuality(lightSensorEvent.getLinkQuality())
                .setLuminosity(lightSensorEvent.getLuminosity())
                .build();
    }

    public MotionSensorAvro motionSensorAvro(MotionSensorEvent motionSensorEvent) {
        return MotionSensorAvro.newBuilder()
                .setMotion(motionSensorEvent.isMotion())
                .setVoltage(motionSensorEvent.getVoltage())
                .setLinkQuality(motionSensorEvent.getLinkQuality())
                .build();
    }

    public SwitchSensorAvro switchSensorAvro(SwitchSensorEvent switchSensorEvent) {
        return SwitchSensorAvro.newBuilder()
                .setState(switchSensorEvent.isState())
                .build();
    }

    public TemperatureSensorAvro temperatureSensorAvro(TemperatureSensorEvent temperatureSensorEvent) {
        return TemperatureSensorAvro.newBuilder()
                .setId(temperatureSensorEvent.getId())
                .setHubId(temperatureSensorEvent.getHubId())
                .setTimestamp(temperatureSensorEvent.getTimestamp())
                .setTemperatureC(temperatureSensorEvent.getTemperatureC())
                .setTemperatureF(temperatureSensorEvent.getTemperatureF())
                .build();
    }

    public ScenarioConditionAvro scenarioConditionAvro(ScenarioCondition scenarioCondition) {
        return ScenarioConditionAvro.newBuilder()
                .setSensorId(scenarioCondition.getSensorId())
                .setValue(scenarioCondition.getValue())
                .setOperation(ConditionOperationAvro.valueOf(String.valueOf(scenarioCondition.getOperation())))
                .setType(ConditionTypeAvro.valueOf(String.valueOf(scenarioCondition.getType())))
                .build();
    }

    public DeviceActionAvro deviceActionAvro(DeviceAction deviceAction) {
        return DeviceActionAvro.newBuilder()
                .setSensorId(deviceAction.getSensorId())
                .setValue(deviceAction.getValue())
                .setType(ActionTypeAvro.valueOf(String.valueOf(deviceAction.getType())))
                .build();

    }

    public HubEventAvro hubEventAvro(ScenarioAddedEvent scenarioAddedEvent) {
        return HubEventAvro.newBuilder()
                .setHubId(scenarioAddedEvent.getHubId())
                .setTimestamp(scenarioAddedEvent.getTimestamp())
                .setPayload(scenarioAddedEvent.getConditions())
                .build();
    }

    public HubEventAvro hubEventAvroAdded(DeviceAddedEvent deviceAddedEvent) {
        return HubEventAvro.newBuilder()
                .setHubId(deviceAddedEvent.getHubId())
                .setTimestamp(deviceAddedEvent.getTimestamp())
                .setPayload(DeviceAddedEventAvro.newBuilder()
                        .setId(deviceAddedEvent.getId())
                        .setType(DeviceTypeAvro.valueOf(deviceAddedEvent.getType().toString())))
                .build();

    }

    public HubEventAvro hubEventAvroRemoved(DeviceRemovedEvent deviceRemovedEvent) {
        return HubEventAvro.newBuilder()
                .setHubId(deviceRemovedEvent.getHubId())
                .setTimestamp(deviceRemovedEvent.getTimestamp())
                .setPayload(DeviceRemovedEventAvro.newBuilder()
                        .setId(deviceRemovedEvent.getId())
                        .build()).build();
    }
}
