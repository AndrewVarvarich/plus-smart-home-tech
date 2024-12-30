package ru.yandex.practicum.event.scenario;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.model.ScenarioEvent;
import ru.yandex.practicum.types.HubEventType;

import java.util.List;

@Getter
@Setter
public class ScenarioAddedEvent extends ScenarioEvent {

    private List<ScenarioCondition> conditions;
    private List<DeviceAction> actions;

    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_ADDED;
    }
}
