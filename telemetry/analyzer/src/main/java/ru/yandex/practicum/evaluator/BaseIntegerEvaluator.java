package ru.yandex.practicum.evaluator;

public abstract class BaseIntegerEvaluator extends BaseOperationEvaluator<Integer> {

    @Override
    public Class<?> getOperandType() {
        return Integer.class;
    }

    @Override
    protected Integer cast(final Object operand) {
        return (Integer) operand;
    }
}