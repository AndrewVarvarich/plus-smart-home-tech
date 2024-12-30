package ru.yandex.practicum.event.hubs;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.model.HubEvent;
import ru.yandex.practicum.types.DeviceType;
import ru.yandex.practicum.types.HubEventType;

@Getter
@Setter
public class DeviceAddedEvent extends HubEvent {

    private DeviceType deviceType;

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_ADDED;
    }
}
