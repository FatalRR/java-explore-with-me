package ru.practicum.main.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class CommentUpdateDto {
    private int id;
    private String text;
    private String oldText;
    private int eventId;
    private String annotation;
    private String authorName;
    private LocalDateTime created;
}