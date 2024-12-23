package ru.yandex.practicum.telemetry.collector.service;

import jakarta.validation.constraints.NotNull;
import ru.yandex.practicum.telemetry.collector.dto.hub.HubEvent;
import ru.yandex.practicum.telemetry.collector.dto.sensor.SensorEvent;

public interface EventService {

    void collectSensorEvent(@NotNull SensorEvent event);

    void collectHubEvent(@NotNull HubEvent event);
}