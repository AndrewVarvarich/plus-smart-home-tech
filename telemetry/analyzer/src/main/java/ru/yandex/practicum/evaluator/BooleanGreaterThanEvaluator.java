package ru.yandex.practicum.evaluator;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.ConditionOperationAvro;

@Component
public class BooleanGreaterThanEvaluator extends BaseBooleanEvaluator {

    @Override
    public ConditionOperationAvro getOperationType() {
        return ConditionOperationAvro.GREATER_THAN;
    }

    @Override
    protected boolean evaluateInternally(final Boolean operandA, final Boolean operandB) {
        return Boolean.compare(operandA, operandB) > 0;
    }
}