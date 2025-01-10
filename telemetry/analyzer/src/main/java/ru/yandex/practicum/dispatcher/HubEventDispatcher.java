package ru.yandex.practicum.dispatcher;

import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

public interface HubEventDispatcher {

    void dispatch(HubEventAvro event);
}