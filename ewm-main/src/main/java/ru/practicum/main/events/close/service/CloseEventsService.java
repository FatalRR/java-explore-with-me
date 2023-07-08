package ru.practicum.main.events.close.service;

import ru.practicum.main.events.dto.EventFullDto;
import ru.practicum.main.events.dto.EventShortDto;
import ru.practicum.main.events.dto.NewEventDto;
import ru.practicum.main.events.dto.UpdateEventUserRequest;
import ru.practicum.main.events.model.Event;

import java.util.List;

public interface CloseEventsService {
    List<EventShortDto> getEventsByUser(int userId, int from, int size);

    EventFullDto createEvents(int userId, NewEventDto newEventDto);

    EventFullDto getEventsByUserFullInfo(int userId, int eventId);

    EventFullDto changeEventsByUser(int userId, int eventId, UpdateEventUserRequest updateEventUserRequest);

    Event getEventById(int eventId);
}