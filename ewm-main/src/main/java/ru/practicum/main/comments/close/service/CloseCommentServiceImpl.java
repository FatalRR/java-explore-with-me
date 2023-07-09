package ru.practicum.main.comments.close.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.comments.dto.CommentByUserDto;
import ru.practicum.main.comments.dto.CommentDto;
import ru.practicum.main.comments.dto.CommentFromRequestDto;
import ru.practicum.main.comments.dto.CommentUpdateDto;
import ru.practicum.main.comments.mapper.CommentMapper;
import ru.practicum.main.comments.model.Comment;
import ru.practicum.main.comments.repository.CommentRepository;
import ru.practicum.main.events.model.Event;
import ru.practicum.main.events.model.EventStatus;
import ru.practicum.main.events.repository.EventsRepository;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.messages.ExceptionMessages;
import ru.practicum.main.users.model.User;
import ru.practicum.main.users.repository.UsersRepository;
import ru.practicum.main.utils.Page;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CloseCommentServiceImpl implements CloseCommentService {
    private final UsersRepository usersRepository;
    private final EventsRepository eventsRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public CommentDto create(CommentFromRequestDto comment, int eventId, int userId) {
        Event event = eventsRepository.findEventsByIdAndStateIs(eventId, EventStatus.PUBLISHED).orElseThrow(
                () -> new NotFoundException(ExceptionMessages.NOT_FOUND_EVENTS_EXCEPTION.label));
        User user = getUserById(userId);
        Comment savedComment = commentRepository.save(CommentMapper.toCommentFromRequest(comment, user, event));
        return CommentMapper.toCommentDto(savedComment);
    }

    @Override
    public List<CommentByUserDto> getAllByUser(int userId, int from, int size) {
        Pageable page = Page.paged(from, size);
        getUserById(userId);
        return commentRepository.findAllByAuthorId(userId, page)
                .stream().map(CommentMapper::toCommentByUserDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto getById(int commentId, int userId) {
        getUserById(userId);
        Comment comment = getCommentById(commentId);
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    @Transactional
    public CommentUpdateDto update(CommentFromRequestDto comment, int userId, int commentId) {
        getUserById(userId);
        Comment old = getCommentById(commentId);
        Comment update = CommentMapper.toCommentFromRequest(comment, old);
        return CommentMapper.toCommentUpdateDto(commentRepository.save(update), old.getText());
    }

    @Override
    @Transactional
    public void delete(int commentId, int userId) {
        getUserById(userId);
        getCommentById(commentId);
        commentRepository.deleteById(commentId);
    }

    private User getUserById(int userId) {
        return usersRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(ExceptionMessages.NOT_FOUND_USERS_EXCEPTION.label));
    }

    private Comment getCommentById(int commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException(ExceptionMessages.NOT_FOUND_COMMENT_EXCEPTION.label));
    }
}