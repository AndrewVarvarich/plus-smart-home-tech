package ru.yandex.practicum.service;

import org.jetbrains.annotations.NotNull;
import ru.yandex.practicum.model.HubEvent;
import ru.yandex.practicum.model.SensorEvent;

public interface EventService {

    void collectSensorEvent(@NotNull SensorEvent event);

    void collectHubEvent(@NotNull HubEvent event);
}
