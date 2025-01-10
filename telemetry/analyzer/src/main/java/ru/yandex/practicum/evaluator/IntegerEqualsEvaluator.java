package ru.yandex.practicum.evaluator;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.ConditionOperationAvro;

@Component
public class IntegerEqualsEvaluator extends BaseIntegerEvaluator {

    @Override
    public ConditionOperationAvro getOperationType() {
        return ConditionOperationAvro.EQUALS;
    }

    @Override
    protected boolean evaluateInternally(final Integer operandA, final Integer operandB) {
        return operandA.equals(operandB);
    }
}