package ru.practicum.main.comments.close.service;

import ru.practicum.main.comments.dto.CommentByUserDto;
import ru.practicum.main.comments.dto.CommentDto;
import ru.practicum.main.comments.dto.CommentFromRequestDto;
import ru.practicum.main.comments.dto.CommentUpdateDto;

import java.util.List;

public interface CloseCommentService {
    CommentDto create(CommentFromRequestDto comment, int eventId, int userId);

    List<CommentByUserDto> getAllByUser(int userId, int from, int size);

    CommentDto getById(int commentId, int userId);

    CommentUpdateDto update(CommentFromRequestDto comment, int userId, int commentId);

    void delete(int commentId, int userId);
}