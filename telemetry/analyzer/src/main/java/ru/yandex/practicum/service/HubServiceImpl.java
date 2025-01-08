package ru.yandex.practicum.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.Hub;
import ru.yandex.practicum.repository.HubRepository;

@Service
public class HubServiceImpl implements HubService {

    private final HubRepository repository;

    public HubServiceImpl(final HubRepository repository) {
        this.repository = repository;
    }

    /**
     * Stub method which always returns true to request if specified hub exists.
     * Will be replaced with real code later.
     *
     */
    @Override
    public boolean existsById(final String id) {
        if (!repository.existsById(id)) {
            final Hub hub = new Hub();
            hub.setId(id);
            repository.save(hub);
        }
        return true;
    }
}