package ru.practicum.main.comments.close.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.comments.close.service.CloseCommentService;
import ru.practicum.main.comments.dto.CommentByUserDto;
import ru.practicum.main.comments.dto.CommentDto;
import ru.practicum.main.comments.dto.CommentFromRequestDto;
import ru.practicum.main.comments.dto.CommentUpdateDto;
import ru.practicum.main.messages.LogMessages;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/comments")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CloseCommentController {
    private final CloseCommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto create(@PathVariable int userId,
                             @RequestParam int eventId,
                             @RequestBody @Valid CommentFromRequestDto newComment) {
        log.debug(LogMessages.TRY_PRIVATE_ADD_COMMENT.label, userId);
        return commentService.create(newComment, eventId, userId);
    }

    @GetMapping
    public List<CommentByUserDto> getAllByUser(@PathVariable int userId,
                                               @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
                                               @RequestParam(name = "size", defaultValue = "10") @Positive int size) {
        log.debug(LogMessages.TRY_PRIVATE_GET_COMMENT_USER_ID.label, userId);
        return commentService.getAllByUser(userId, from, size);
    }

    @GetMapping("/{commentId}")
    public CommentDto getById(@PathVariable int userId,
                              @PathVariable int commentId) {
        log.debug(LogMessages.TRY_PRIVATE_GET_COMMENT.label, userId);
        return commentService.getById(commentId, userId);
    }

    @PatchMapping("/{commentId}")
    public CommentUpdateDto update(@PathVariable int userId,
                                   @PathVariable int commentId,
                                   @RequestBody @Valid CommentFromRequestDto comment) {
        log.debug(LogMessages.TRY_PRIVATE_UPDATE_COMMENT.label, userId);
        return commentService.update(comment, userId, commentId);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable int userId,
                       @PathVariable int commentId) {
        log.debug(LogMessages.TRY_PRIVATE_DELETE_COMMENT.label, userId);
        commentService.delete(commentId, userId);
    }
}