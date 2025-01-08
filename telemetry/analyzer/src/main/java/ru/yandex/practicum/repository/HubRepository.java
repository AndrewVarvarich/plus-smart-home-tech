package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.Hub;

public interface HubRepository extends JpaRepository<Hub, String> {

}