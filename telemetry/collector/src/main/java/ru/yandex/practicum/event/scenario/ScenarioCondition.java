package ru.yandex.practicum.event.scenario;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.types.ConditionType;
import ru.yandex.practicum.types.OperationType;

@Getter
@Setter
public class ScenarioCondition {

    private String sensorId;
    private ConditionType type;
    private OperationType operation;
    private Integer value;
}
