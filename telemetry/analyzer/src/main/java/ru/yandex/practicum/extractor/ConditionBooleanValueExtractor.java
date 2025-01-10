package ru.yandex.practicum.extractor;

import org.springframework.stereotype.Component;

@Component
public class ConditionBooleanValueExtractor implements ConditionValueExtractor {

    @Override
    public String getValueType() {
        return Boolean.class.getSimpleName();
    }

    @Override
    public Object extractValue(final String data) {
        return Boolean.parseBoolean(data);
    }
}