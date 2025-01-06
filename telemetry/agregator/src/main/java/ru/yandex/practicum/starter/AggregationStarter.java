package ru.yandex.practicum.starter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.service.SnapshotService;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Duration;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class AggregationStarter {

    @Value("${topics.sensors}")
    private String sensorsTopic;

    @Value("${bootstrap_server}")
    private String bootstrapServer;

    @Value("${key_deserializer_class}")
    private String keyDeserializerClass;

    @Value("${value_deserializer_class}")
    private String valueDeserializerClass;

    @Value("${key_serializer_class}")
    private String keySerializerClass;

    @Value("${value_serializer_class}")
    private String valueSerializerClass;

    @Value("${topics.snapshots}")
    private String sensorsSnapshotsTopic;

    private final SnapshotService service;

    Map<String, SensorsSnapshotAvro> snapshots = new HashMap<>();

    public void start() {

        Producer<String, SpecificRecordBase> producer;

        Properties producerConfig = new Properties();
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializerClass);
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializerClass);

        producer = new KafkaProducer<>(producerConfig);

        Properties consumerConfig = new Properties();

        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializerClass);
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializerClass);
        consumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        Consumer<String, SensorEventAvro> consumer = new KafkaConsumer<>(consumerConfig);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Added a shutdown hook");
            consumer.wakeup();
        }));

        try {
            consumer.subscribe(List.of(sensorsTopic));
            while (true) {
                ConsumerRecords<String, SensorEventAvro> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, SensorEventAvro> record : records) {
                    try {
                        log.info("Handling message from partition {}, offset {}:\n{}\n",
                                record.partition(), record.offset(), record.value());
                        Optional<SensorsSnapshotAvro> optionalSensorsSnapshotAvro = service.updateState(record.value());
                        optionalSensorsSnapshotAvro.ifPresent(sensorsSnapshotAvro -> {
                            snapshots.put(record.value().getHubId(), sensorsSnapshotAvro);

                            producer.send(new ProducerRecord<>(sensorsSnapshotsTopic, sensorsSnapshotAvro), (metadata, exception) -> {
                                if (exception != null) {
                                    log.error("Error sending message: {}", sensorsSnapshotAvro, exception);
                                } else {
                                    log.info("{} sent. Metadata: {}", sensorsSnapshotAvro, metadata);
                                }
                            });
                        });
                    } catch (Exception e) {
                        log.error("Error handling message: {}", record.value(), e);
                    }
                }
                try {
                    producer.flush();
                    consumer.commitSync();
                    log.info("Offsets committed.");
                } catch (CommitFailedException e) {
                    log.error("Error committing offsets", e);
                }
            }
        } catch (WakeupException ignored) {
            log.info("Consumer interrupted.");
        } catch (Exception e) {
            log.error("Error when consuming messages", e);
        } finally {
            try {
                consumer.close();
                producer.close();
            } catch (Exception e) {
                log.error("Error closing resources", e);
            }
        }
    }

}