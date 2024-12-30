package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.model.HubEvent;
import ru.yandex.practicum.model.SensorEvent;
import ru.yandex.practicum.service.EventService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@Slf4j
public class EventController {

    private final EventService eventService;

    @PostMapping("/sensors")
    public void collectSensorEvent(@RequestBody SensorEvent event) {
        log.info("Sensor event received");
        eventService.collectSensorEvent(event);
        log.info("Sensor event processed");
    }

    @PostMapping("/hubs")
    public void collectHubEvent(@RequestBody HubEvent event) {
        log.info("Hub event received");
        eventService.collectHubEvent(event);
        log.info("Hub event processed");
    }
}
