package ru.yandex.practicum.processor;

import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

public abstract class BaseHubEventProcessor<T extends SpecificRecordBase> implements HubEventProcessor {

    @Override
    public void handle(final HubEventAvro event) {
        if (event.getPayload().getClass() != getPayloadType()) {
            throw new IllegalArgumentException("Unknown payload type: " + event.getPayload().getClass());
        }
        final String hubId = event.getHubId();
        final T payload = cast(event.getPayload());
        handleInternally(hubId, payload);
    }

    protected abstract T cast(Object payload);

    protected abstract void handleInternally(String hubId, T payload);
}