package ru.yandex.practicum.extractor;

import org.springframework.stereotype.Component;

@Component
public class ConditionIntegerValueExtractor implements ConditionValueExtractor {

    @Override
    public String getValueType() {
        return Integer.class.getSimpleName();
    }

    @Override
    public Object extractValue(final String data) {
        return Integer.parseInt(data);
    }
}