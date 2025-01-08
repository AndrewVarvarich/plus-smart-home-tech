package ru.yandex.practicum.extractor;

public interface ConditionValueExtractor {

    String getValueType();

    Object extractValue(String data);
}