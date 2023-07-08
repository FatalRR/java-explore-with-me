package ru.practicum.main.requests.close.service;

import ru.practicum.main.requests.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main.requests.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface CloseRequestsService {
    List<ParticipationRequestDto> getRequestsByUserOtherEvents(int userId);

    ParticipationRequestDto createRequestsByUserOtherEvents(int userId, int eventId);

    ParticipationRequestDto cancelRequestsByUserOtherEvents(int userId, int requestId);

    List<ParticipationRequestDto> getRequestsByUser(int userId, int eventId);

    EventRequestStatusUpdateResult changeStatusRequestsByUser(int userId, int eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);
}