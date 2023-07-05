package ru.practicum.main.events.open.service;

import ru.practicum.main.events.dto.EventFullDto;
import ru.practicum.main.events.dto.OpenEventRequests;
import ru.practicum.main.events.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OpenEventsService {
    List<EventShortDto> getEvents(OpenEventRequests requests, HttpServletRequest request);

    EventFullDto getEventsById(int eventId, HttpServletRequest request);
}