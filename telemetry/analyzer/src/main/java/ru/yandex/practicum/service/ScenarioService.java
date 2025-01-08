package ru.yandex.practicum.service;

import ru.yandex.practicum.model.Scenario;

import java.util.Collection;

public interface ScenarioService {

    void save(Scenario scenario);

    Collection<Scenario> getByHubId(String hubId);

    void delete(Scenario scenario);
}