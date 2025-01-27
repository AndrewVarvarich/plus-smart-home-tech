package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.kafka.telemetry.event.ActionTypeAvro;

import java.util.UUID;

@Entity
@Table(name = "device_actions")
@Data
@EqualsAndHashCode(of = {"scenarioId", "deviceId", "type"})
public class DeviceAction {

    @Id
    private UUID id;

    private UUID scenarioId;
    private String deviceId;

    @Enumerated(EnumType.STRING)
    private ActionTypeAvro type;

    @Column(name = "\"value\"")
    private Integer value;
}