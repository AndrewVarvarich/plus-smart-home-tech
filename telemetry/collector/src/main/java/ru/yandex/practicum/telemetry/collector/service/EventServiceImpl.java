package ru.yandex.practicum.telemetry.collector.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.telemetry.collector.dto.hub.DeviceAddedEvent;
import ru.yandex.practicum.telemetry.collector.dto.hub.DeviceRemovedEvent;
import ru.yandex.practicum.telemetry.collector.dto.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.dto.hub.ScenarioAddedEvent;
import ru.yandex.practicum.telemetry.collector.dto.hub.ScenarioRemovedEvent;
import ru.yandex.practicum.telemetry.collector.dto.sensor.ClimateSensorEvent;
import ru.yandex.practicum.telemetry.collector.dto.sensor.LightSensorEvent;
import ru.yandex.practicum.telemetry.collector.dto.sensor.MotionSensorEvent;
import ru.yandex.practicum.telemetry.collector.dto.sensor.SensorEvent;
import ru.yandex.practicum.telemetry.collector.dto.sensor.SwitchSensorEvent;
import ru.yandex.practicum.telemetry.collector.dto.sensor.TemperatureSensorEvent;
import ru.yandex.practicum.telemetry.collector.mapper.EventMapper;
import ru.yandex.practicum.telemetry.collector.util.KafkaClient;
import ru.yandex.practicum.telemetry.collector.util.KafkaSender;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;


import java.time.Clock;
import java.time.Instant;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    private final String sensorKafkaTopic;
    private final String hubKafkaTopic;
    private final EventMapper mapper;
    private final KafkaSender sender;
    private final Clock clock;

    public EventServiceImpl(
            @Value("${kafka.topics.sensor}") final String sensorKafkaTopic,
            @Value("${kafka.topics.hub}") final String hubKafkaTopic,
            final EventMapper mapper,
            final KafkaClient client,
            final Clock clock) {
        this.hubKafkaTopic = hubKafkaTopic;
        this.sensorKafkaTopic = sensorKafkaTopic;
        this.mapper = mapper;
        this.sender = client.getSender();
        this.clock = clock;
    }

    @Override
    public void collectSensorEvent(final SensorEvent event) {
        if (event.getTimestamp() == null) {
            event.setTimestamp(Instant.now(clock));
        }
        final SensorEventAvro sensorEventAvro =
                switch (event) {
                    case ClimateSensorEvent climateSensorEvent -> mapper.mapToAvro(climateSensorEvent);
                    case LightSensorEvent lightSensorEvent -> mapper.mapToAvro(lightSensorEvent);
                    case MotionSensorEvent motionSensorEvent -> mapper.mapToAvro(motionSensorEvent);
                    case SwitchSensorEvent switchSensorEvent -> mapper.mapToAvro(switchSensorEvent);
                    case TemperatureSensorEvent temperatureSensorEvent -> mapper.mapToAvro(temperatureSensorEvent);
                    case null, default -> throw new AssertionError();
                };
        final String key = sensorEventAvro.getHubId();
        final long timestamp = sensorEventAvro.getTimestamp().toEpochMilli();
        sender.send(sensorKafkaTopic, key, timestamp, sensorEventAvro);
        log.debug("Sent sensor event to topic [{}]: {}", sensorKafkaTopic, sensorEventAvro);
    }

    @Override
    public void collectHubEvent(final HubEvent event) {
        if (event.getTimestamp() == null) {
            event.setTimestamp(Instant.now(clock));
        }
        final HubEventAvro hubEventAvro =
                switch (event) {
                    case DeviceAddedEvent deviceAddedEvent -> mapper.mapToAvro(deviceAddedEvent);
                    case DeviceRemovedEvent deviceRemovedEvent -> mapper.mapToAvro(deviceRemovedEvent);
                    case ScenarioAddedEvent scenarioAddedEvent -> mapper.mapToAvro(scenarioAddedEvent);
                    case ScenarioRemovedEvent scenarioRemovedEvent -> mapper.mapToAvro(scenarioRemovedEvent);
                    case null, default -> throw new AssertionError();
                };
        final String key = hubEventAvro.getHubId();
        final long timestamp = hubEventAvro.getTimestamp().toEpochMilli();
        sender.send(hubKafkaTopic, key, timestamp, hubEventAvro);
        log.debug("Sent hub event to topic [{}]: {}", hubKafkaTopic, hubEventAvro);
    }
}