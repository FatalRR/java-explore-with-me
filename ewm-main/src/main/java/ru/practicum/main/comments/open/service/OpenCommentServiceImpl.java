package ru.practicum.main.comments.open.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.comments.dto.CommentDto;
import ru.practicum.main.comments.mapper.CommentMapper;
import ru.practicum.main.comments.repository.CommentRepository;
import ru.practicum.main.events.model.EventStatus;
import ru.practicum.main.events.repository.EventsRepository;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.messages.ExceptionMessages;
import ru.practicum.main.utils.Page;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OpenCommentServiceImpl implements OpenCommentService {
    private final CommentRepository commentRepository;
    private final EventsRepository eventsRepository;

    @Override
    public List<CommentDto> getAllByEvent(int eventId, int from, int size) {
        Pageable page = Page.paged(from, size);
        eventsRepository.findEventsByIdAndStateIs(eventId, EventStatus.PUBLISHED).orElseThrow(
                () -> new NotFoundException(ExceptionMessages.NOT_FOUND_EVENTS_EXCEPTION.label));
        return commentRepository.findAll(page)
                .stream().map(CommentMapper::toCommentDto).collect(Collectors.toList());
    }
}