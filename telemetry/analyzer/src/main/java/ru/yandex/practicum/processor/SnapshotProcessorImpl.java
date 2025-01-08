package ru.yandex.practicum.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.model.ScenarioCondition;
import ru.yandex.practicum.service.DeviceActionExecutor;
import ru.yandex.practicum.service.PredicateBuilder;
import ru.yandex.practicum.service.ScenarioService;

import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.List;
import java.util.function.Predicate;

@Component
public class SnapshotProcessorImpl implements SnapshotProcessor {

    private static final Logger log = LoggerFactory.getLogger(SnapshotProcessorImpl.class);
    private final ScenarioService scenarioService;
    private final PredicateBuilder predicateBuilder;
    private final DeviceActionExecutor executor;

    public SnapshotProcessorImpl(
            final ScenarioService scenarioService,
            final PredicateBuilder predicateBuilder,
            final DeviceActionExecutor executor) {
        this.scenarioService = scenarioService;
        this.predicateBuilder = predicateBuilder;
        this.executor = executor;
    }

    @Override
    public void handle(final SensorsSnapshotAvro snapshot) {
        log.debug("Processing snapshot... {}", snapshot);
        scenarioService.getByHubId(snapshot.getHubId()).stream()
                .filter(scenario -> toPredicate(scenario.getConditions()).test(snapshot))
                .forEach(executor::execute);
        log.debug("Snapshot processed {}", snapshot);
    }

    private Predicate<SensorsSnapshotAvro> toPredicate(final List<ScenarioCondition> conditions) {
        return conditions.stream()
                .map(predicateBuilder::toPredicate)
                .reduce(Predicate::and)
                .orElseThrow();
    }
}