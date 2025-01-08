package ru.yandex.practicum.dispatcher;

import org.apache.avro.specific.SpecificRecordBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.processor.HubEventProcessor;
import ru.yandex.practicum.exception.EntityValidationException;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class HubEventDispatcherImpl implements HubEventDispatcher {

    private static final Logger log = LoggerFactory.getLogger(HubEventDispatcherImpl.class);
    private final Map<Class<? extends SpecificRecordBase>, HubEventProcessor> handlers;

    public HubEventDispatcherImpl(final Set<HubEventProcessor> handlers) {
        this.handlers = handlers.stream()
                .collect(Collectors.toMap(HubEventProcessor::getPayloadType, Function.identity()));
    }

    public void dispatch(final HubEventAvro event) {
        final Class<?> clazz = event.getPayload().getClass();
        if (handlers.containsKey(clazz)) {
            try {
                handlers.get(clazz).handle(event);
            } catch (EntityValidationException e) {
                log.warn(e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("No handler of payload of " + clazz);
        }
    }
}