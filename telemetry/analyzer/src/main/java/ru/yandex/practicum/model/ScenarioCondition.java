package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.kafka.telemetry.event.ConditionOperationAvro;
import ru.yandex.practicum.kafka.telemetry.event.ConditionTypeAvro;

import java.util.UUID;

@Entity
@Table(name = "scenario_conditions")
@Data
@EqualsAndHashCode(of = {"scenarioId", "deviceId", "conditionType", "operation"})
public class ScenarioCondition {

    @Id
    private UUID id;

    private UUID scenarioId;
    private String deviceId;

    @Enumerated(EnumType.STRING)
    private ConditionTypeAvro conditionType;

    @Enumerated(EnumType.STRING)
    private ConditionOperationAvro operation;

    private String valueType;
    @Column(name = "\"value\"")
    private String value;
}