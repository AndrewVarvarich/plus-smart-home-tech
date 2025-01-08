package ru.yandex.practicum.service;

import ru.yandex.practicum.model.Scenario;

public interface DeviceActionExecutor {

    void execute(Scenario scenario);
}