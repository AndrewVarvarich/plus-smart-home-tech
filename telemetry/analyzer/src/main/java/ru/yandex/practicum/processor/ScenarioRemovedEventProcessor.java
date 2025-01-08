package ru.yandex.practicum.processor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;
import ru.yandex.practicum.model.Scenario;
import ru.yandex.practicum.service.ScenarioService;

@Component
public class ScenarioRemovedEventProcessor extends BaseHubEventProcessor<ScenarioRemovedEventAvro> {

    private final ScenarioService service;

    public ScenarioRemovedEventProcessor(final ScenarioService service) {
        this.service = service;
    }

    @Override
    public Class<ScenarioRemovedEventAvro> getPayloadType() {
        return ScenarioRemovedEventAvro.class;
    }

    @Override
    protected ScenarioRemovedEventAvro cast(final Object payload) {
        return (ScenarioRemovedEventAvro) payload;
    }

    @Override
    protected void handleInternally(final String hubId, final ScenarioRemovedEventAvro payload) {
        final Scenario scenario = new Scenario();
        scenario.setName(payload.getName());
        scenario.setHubId(hubId);
        service.delete(scenario);
    }
}