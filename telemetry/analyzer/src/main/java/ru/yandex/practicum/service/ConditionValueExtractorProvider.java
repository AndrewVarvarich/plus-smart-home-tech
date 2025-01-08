package ru.yandex.practicum.service;

import java.util.function.Supplier;

import ru.yandex.practicum.model.ScenarioCondition;


public interface ConditionValueExtractorProvider {

    Supplier<Object> getExtractor(ScenarioCondition condition);
}