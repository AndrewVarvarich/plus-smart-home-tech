package ru.yandex.practicum.event.scenario;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.types.ActionType;

@Getter
@Setter
public class DeviceAction {

    private String sensorId;
    private ActionType type;
    private Integer value;

}
