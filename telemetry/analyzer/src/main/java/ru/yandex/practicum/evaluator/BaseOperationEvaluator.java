package ru.yandex.practicum.evaluator;

public abstract class BaseOperationEvaluator<T> implements OperationEvaluator {

    @Override
    public boolean evaluate(final Object operandA, final Object operandB) {
        checkOperandType(operandA);
        checkOperandType(operandB);
        final T _operandA = cast(operandA);
        final T _operandB = cast(operandB);
        return evaluateInternally(_operandA, _operandB);
    }

    protected abstract T cast(Object operand);

    protected abstract boolean evaluateInternally(T operandA, T operandB);

    protected void checkOperandType(final Object operand) {
        if (operand.getClass() != getOperandType()) {
            throw new IllegalArgumentException("Unsupported operand type " + operand.getClass());
        }
    }
}