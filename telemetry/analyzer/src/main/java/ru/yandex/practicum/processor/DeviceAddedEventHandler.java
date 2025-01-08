package ru.yandex.practicum.processor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.model.Device;
import ru.yandex.practicum.service.DeviceService;

@Component
public class DeviceAddedEventHandler extends BaseHubEventProcessor<DeviceAddedEventAvro> {

    private final DeviceService service;

    public DeviceAddedEventHandler(final DeviceService service) {
        this.service = service;
    }

    @Override
    public Class<DeviceAddedEventAvro> getPayloadType() {
        return DeviceAddedEventAvro.class;
    }

    @Override
    protected DeviceAddedEventAvro cast(final Object payload) {
        return (DeviceAddedEventAvro) payload;
    }

    @Override
    protected void handleInternally(final String hubId, final DeviceAddedEventAvro payload) {
        final Device device = new Device();
        device.setId(payload.getId());
        device.setHubId(hubId);
        device.setType(payload.getType());
        service.register(device);
    }
}