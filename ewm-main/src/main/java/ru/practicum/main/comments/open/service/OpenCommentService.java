package ru.practicum.main.comments.open.service;

import ru.practicum.main.comments.dto.CommentDto;

import java.util.List;

public interface OpenCommentService {
    List<CommentDto> getAllByEvent(int eventId, int from, int size);
}