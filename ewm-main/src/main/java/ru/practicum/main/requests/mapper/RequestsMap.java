package ru.practicum.main.requests.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.requests.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.requests.dto.ParticipationRequestDto;
import ru.practicum.main.requests.model.ParticipationRequest;
import ru.practicum.main.requests.model.StatusEventRequestUpdateResult;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class RequestsMap {
    public static ParticipationRequestDto mapToParticipationRequestDto(ParticipationRequest participationRequest) {
        return ParticipationRequestDto.builder()
                .id(participationRequest.getId())
                .created(participationRequest.getCreated().toString().replace("T", " ").substring(0, participationRequest.getCreated().toString().indexOf(".")))
                .event(participationRequest.getEventsWithRequests().getId())
                .requester(participationRequest.getRequester().getId())
                .status(participationRequest.getStatus())
                .build();
    }

    public static List<ParticipationRequestDto> mapToListParticipationRequestDto(List<ParticipationRequest> listParticipationRequest) {
        List<ParticipationRequestDto> listParticipationRequestDto = new ArrayList<>();
        for (ParticipationRequest request : listParticipationRequest) {
            listParticipationRequestDto.add(mapToParticipationRequestDto(request));
        }
        return listParticipationRequestDto;
    }

    public static EventRequestStatusUpdateResult mapToEventRequestStatusUpdateResult(List<ParticipationRequest> requestList) {
        List<ParticipationRequestDto> requestsConfirmed = new ArrayList<>();
        List<ParticipationRequestDto> requestsRejected = new ArrayList<>();

        for (ParticipationRequest request : requestList) {
            if (request.getStatus().equals(StatusEventRequestUpdateResult.CONFIRMED)) {
                requestsConfirmed.add(mapToParticipationRequestDto(request));
            }
            if (request.getStatus().equals(StatusEventRequestUpdateResult.REJECTED)) {
                requestsRejected.add(mapToParticipationRequestDto(request));
            }
        }

        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(requestsConfirmed)
                .rejectedRequests(requestsRejected)
                .build();
    }
}