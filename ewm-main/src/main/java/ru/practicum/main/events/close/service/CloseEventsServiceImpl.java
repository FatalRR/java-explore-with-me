package ru.practicum.main.events.close.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.categories.mapper.CategoryMapper;
import ru.practicum.main.categories.model.Category;
import ru.practicum.main.categories.repository.CategoriesRepository;
import ru.practicum.main.events.dto.EventFullDto;
import ru.practicum.main.events.dto.EventShortDto;
import ru.practicum.main.events.dto.NewEventDto;
import ru.practicum.main.events.dto.UpdateEventUserRequest;
import ru.practicum.main.events.mapper.EventsMapper;
import ru.practicum.main.events.model.Event;
import ru.practicum.main.events.model.EventStatus;
import ru.practicum.main.events.repository.EventsRepository;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.exception.ValidTimeException;
import ru.practicum.main.locations.repository.LocationRepository;
import ru.practicum.main.messages.ExceptionMessages;
import ru.practicum.main.messages.LogMessages;
import ru.practicum.main.users.model.User;
import ru.practicum.main.users.repository.UsersRepository;
import ru.practicum.main.utils.Constants;
import ru.practicum.main.utils.Page;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CloseEventsServiceImpl implements CloseEventsService {
    private final EventsRepository repository;
    private final UsersRepository usersRepository;
    private final CategoriesRepository categoriesRepository;
    private final LocationRepository locationRepository;

    @Transactional(readOnly = true)
    @Override
    public List<EventShortDto> getEventsByUser(int userId, int from, int size) {
        Pageable page = Page.paged(from, size);
        return EventsMapper.mapToListEventShortDto(repository.findEventsByInitiator_Id(userId, page));
    }

    @Override
    public EventFullDto createEvents(int userId, NewEventDto newEventDto) {
        validTime(newEventDto.getEventDate());

        User user = usersRepository.findById(userId).orElseThrow(() -> new NotFoundException(ExceptionMessages.NOT_FOUND_USERS_EXCEPTION.label));
        Category category = categoriesRepository.findById(newEventDto.getCategory()).orElseThrow(() -> new NotFoundException(ExceptionMessages.NOT_FOUND_CATEGORIES_EXCEPTION.label));
        locationRepository.save(newEventDto.getLocation());

        log.debug(LogMessages.PRIVATE_POST_EVENT_USER_ID.label, userId);
        return EventsMapper.mapToEventFullDto(repository.save(EventsMapper.mapToEvent(newEventDto, category, user)));
    }

    @Transactional(readOnly = true)
    @Override
    public EventFullDto getEventsByUserFullInfo(int userId, int eventId) {
        log.debug(LogMessages.PRIVATE_GET_EVENT_USER.label, userId);
        return EventsMapper.mapToEventFullDto(repository.findEventByIdAndInitiator_Id(eventId, userId).orElseThrow(() -> new NotFoundException(ExceptionMessages.NOT_FOUND_EVENTS_EXCEPTION.label)));
    }

    @Override
    public EventFullDto changeEventsByUser(int userId, int eventId, UpdateEventUserRequest updateEventUserRequest) {
        Event event = repository.findById(eventId).orElseThrow(() -> new NotFoundException(ExceptionMessages.NOT_FOUND_EVENTS_EXCEPTION.label));

        if (event.getState().equals(EventStatus.PENDING) || event.getState().equals(EventStatus.CANCELED)) {
            if (updateEventUserRequest.getEventDate() != null) {
                validTime(updateEventUserRequest.getEventDate());
                event.setEventDate(LocalDateTime.parse(updateEventUserRequest.getEventDate()));
            }
            if (updateEventUserRequest.getAnnotation() != null) {
                event.setAnnotation(updateEventUserRequest.getAnnotation());
            }
            if (updateEventUserRequest.getCategory() != null) {
                event.setCategory(CategoryMapper.mapToCategory(updateEventUserRequest.getCategory()));
            }
            if (updateEventUserRequest.getDescription() != null) {
                event.setDescription(updateEventUserRequest.getDescription());
            }
            if (updateEventUserRequest.getLocation() != null) {
                event.setLocation(updateEventUserRequest.getLocation());
            }
            if (updateEventUserRequest.getPaid() != null) {
                event.setPaid(updateEventUserRequest.getPaid());
            }
            if (updateEventUserRequest.getParticipantLimit() != null) {
                event.setParticipantLimit(updateEventUserRequest.getParticipantLimit());
            }
            if (updateEventUserRequest.getRequestModeration() != null) {
                event.setRequestModeration(updateEventUserRequest.getRequestModeration());
            }
            if (updateEventUserRequest.getStateAction() != null) {
                switch (updateEventUserRequest.getStateAction()) {
                    case SEND_TO_REVIEW:
                        event.setState(EventStatus.PENDING);
                        break;
                    case CANCEL_REVIEW:
                        event.setState(EventStatus.CANCELED);
                        break;
                }
            }
            if (updateEventUserRequest.getTitle() != null) {
                event.setTitle(updateEventUserRequest.getTitle());
            }

            log.debug(LogMessages.PRIVATE_PATCH_EVENT_ID.label, eventId);
            return EventsMapper.mapToEventFullDto(repository.save(event));
        } else {
            throw new ConflictException(ExceptionMessages.CONFLICT_EXCEPTION.label);
        }
    }

    @Override
    public Event getEventById(int eventId) {
        return repository.findById(eventId).orElseThrow(() -> new NotFoundException(ExceptionMessages.NOT_FOUND_EVENTS_EXCEPTION.label));
    }

    private void validTime(String time) {
        LocalDateTime startDate = LocalDateTime.parse(time, Constants.formatter);

        if (Duration.between(LocalDateTime.now(), startDate).toMinutes() < Duration.ofHours(2).toMinutes()) {
            throw new ValidTimeException(ExceptionMessages.VALID_TIME_EXCEPTION.label);
        }
    }
}