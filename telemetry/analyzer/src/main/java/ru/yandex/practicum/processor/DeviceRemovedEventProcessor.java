package ru.yandex.practicum.processor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.model.Device;
import ru.yandex.practicum.service.DeviceService;

@Component
public class DeviceRemovedEventProcessor extends BaseHubEventProcessor<DeviceRemovedEventAvro> {

    private final DeviceService service;

    public DeviceRemovedEventProcessor(final DeviceService service) {
        this.service = service;
    }

    @Override
    public Class<DeviceRemovedEventAvro> getPayloadType() {
        return DeviceRemovedEventAvro.class;
    }

    @Override
    protected DeviceRemovedEventAvro cast(final Object payload) {
        return (DeviceRemovedEventAvro) payload;
    }

    @Override
    protected void handleInternally(final String hubId, final DeviceRemovedEventAvro payload) {
        final Device device = new Device();
        device.setId(payload.getId());
        device.setHubId(hubId);
        service.deregister(device);
    }
}