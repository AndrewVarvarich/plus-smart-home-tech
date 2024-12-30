package ru.yandex.practicum.event.scenario;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.model.ScenarioEvent;
import ru.yandex.practicum.types.HubEventType;

@Getter
@Setter
public class ScenarioRemovedEvent extends ScenarioEvent {

    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_REMOVED;
    }
}
