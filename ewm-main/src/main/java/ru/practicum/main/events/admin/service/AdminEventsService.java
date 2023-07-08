package ru.practicum.main.events.admin.service;

import ru.practicum.main.events.dto.AdminEventRequests;
import ru.practicum.main.events.dto.EventFullDto;
import ru.practicum.main.events.dto.UpdateEventAdminRequest;

import java.util.List;

public interface AdminEventsService {
    List<EventFullDto> findEvents(AdminEventRequests requests);

    EventFullDto changeEvents(int eventId, UpdateEventAdminRequest updateEventAdminRequest);
}
