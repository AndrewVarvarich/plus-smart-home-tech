package ru.yandex.practicum.service;

import ru.yandex.practicum.model.Device;

public interface DeviceService {

    void register(Device device);

    void deregister(Device device);
}