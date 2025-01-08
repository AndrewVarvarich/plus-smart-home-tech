package ru.yandex.practicum.service;

import ru.yandex.practicum.model.ScenarioCondition;

import java.util.function.Supplier;

public interface ConditionValueExtractorFactory {

    Supplier<Object> getExtractor(ScenarioCondition condition);
}