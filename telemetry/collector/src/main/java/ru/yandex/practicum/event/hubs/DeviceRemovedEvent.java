package ru.yandex.practicum.event.hubs;


import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.model.HubEvent;
import ru.yandex.practicum.types.HubEventType;

@Getter
@Setter
public class DeviceRemovedEvent extends HubEvent {
    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_REMOVED;
    }
}
