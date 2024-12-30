package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.configuration.ProducedConfig;
import ru.yandex.practicum.event.hubs.DeviceAddedEvent;
import ru.yandex.practicum.event.hubs.DeviceRemovedEvent;
import ru.yandex.practicum.event.sensor.*;
import ru.yandex.practicum.mapper.EventMapper;
import ru.yandex.practicum.model.HubEvent;
import ru.yandex.practicum.model.SensorEvent;
import ru.yandex.practicum.types.TopicType;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService, AutoCloseable {

    private final KafkaProducer<String, SpecificRecordBase> kafkaProducer;
    private final ProducedConfig producerConfig;
    private final EventMapper eventMapper;

    @Override
    public void collectSensorEvent(@NotNull SensorEvent event) {
        String hubId = event.getHubId();
        long eventTimestamp = event.getTimestamp().toEpochMilli();

        String topic = producerConfig.getTopic(TopicType.SENSOR);
        switch (event.getType()) {
            case CLIMATE_SENSOR_EVENT:
                kafkaProducer.send(new ProducerRecord<>(topic, null, eventTimestamp, hubId, eventMapper
                        .climateSensorAvro((ClimateSensorEvent) event)));
                break;
            case LIGHT_SENSOR_EVENT:
                kafkaProducer.send(new ProducerRecord<>(topic, null, eventTimestamp, hubId, eventMapper
                        .lightSensorAvro((LightSensorEvent) event)));
                break;
            case MOTION_SENSOR_EVENT:
                kafkaProducer.send(new ProducerRecord<>(topic, null, eventTimestamp, hubId, eventMapper
                        .motionSensorAvro((MotionSensorEvent) event)));
                break;
            case SWITCH_SENSOR_EVENT:
                kafkaProducer.send(new ProducerRecord<>(topic, null, eventTimestamp, hubId, eventMapper
                        .switchSensorAvro((SwitchSensorEvent) event)));
                break;
            case TEMPERATURE_SENSOR_EVENT:
                kafkaProducer.send(new ProducerRecord<>(topic, null, eventTimestamp, hubId, eventMapper
                        .temperatureSensorAvro((TemperatureSensorEvent) event)));
                break;
            default:
                throw new IllegalArgumentException("Unknown event type: " + event.getType());
        }
    }

    @Override
    public void collectHubEvent(@NotNull HubEvent event) {
        String hubId = event.getHubId();
        long eventTimestamp = event.getTimestamp().toEpochMilli();

        String topic = producerConfig.getTopic(TopicType.HUB);
        switch (event.getType()) {
            case DEVICE_ADDED:
                kafkaProducer.send(new ProducerRecord<>(topic, null, eventTimestamp, hubId, eventMapper
                        .hubEventAvroAdded((DeviceAddedEvent) event)));
                break;
            case DEVICE_REMOVED:
                kafkaProducer.send(new ProducerRecord<>(topic, null, eventTimestamp, hubId, eventMapper
                        .hubEventAvroRemoved((DeviceRemovedEvent) event)));
                break;
            default:
                throw new IllegalArgumentException("Unknown event type: " + event.getType());
        }
    }

    @Override
    public void close() throws Exception {
        kafkaProducer.flush();
        kafkaProducer.close();
    }
}
