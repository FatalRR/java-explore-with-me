package ru.practicum.main.comments.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.comments.dto.CommentByUserDto;
import ru.practicum.main.comments.dto.CommentDto;
import ru.practicum.main.comments.dto.CommentFromRequestDto;
import ru.practicum.main.comments.dto.CommentUpdateDto;
import ru.practicum.main.comments.model.Comment;
import ru.practicum.main.events.model.Event;
import ru.practicum.main.users.model.User;

import java.time.LocalDateTime;

@UtilityClass
public class CommentMapper {
    public static Comment toCommentFromRequest(CommentFromRequestDto comment, User author, Event event) {
        return Comment.builder()
                .text(comment.getText())
                .event(event)
                .author(author)
                .created(LocalDateTime.now())
                .build();
    }

    public static Comment toCommentFromRequest(CommentFromRequestDto comment, Comment old) {
        return Comment.builder()
                .id(old.getId())
                .text(comment.getText())
                .event(old.getEvent())
                .author(old.getAuthor())
                .created(old.getCreated())
                .build();
    }

    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .eventId(comment.getEvent().getId())
                .annotation(comment.getEvent().getAnnotation())
                .authorName(comment.getAuthor().getName())
                .created(comment.getCreated())
                .build();
    }

    public static CommentUpdateDto toCommentUpdateDto(Comment comment, String oldText) {
        return CommentUpdateDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .oldText(oldText)
                .eventId(comment.getEvent().getId())
                .annotation(comment.getEvent().getAnnotation())
                .authorName(comment.getAuthor().getName())
                .created(comment.getCreated())
                .build();
    }

    public static CommentByUserDto toCommentByUserDto(Comment comment) {
        return CommentByUserDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .eventId(comment.getEvent().getId())
                .annotation(comment.getEvent().getAnnotation())
                .created(comment.getCreated())
                .build();
    }
}