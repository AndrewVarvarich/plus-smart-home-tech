package ru.yandex.practicum.service;

import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.model.ScenarioCondition;

import java.util.function.Predicate;

public interface PredicateBuilder {

    Predicate<SensorsSnapshotAvro> toPredicate(ScenarioCondition condition);
}