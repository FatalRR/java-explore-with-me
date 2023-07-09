package ru.practicum.main.comments.open.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.comments.dto.CommentDto;
import ru.practicum.main.comments.open.service.OpenCommentService;
import ru.practicum.main.messages.LogMessages;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/comments")
@RequiredArgsConstructor
@Validated
@Slf4j
public class OpenCommentController {
    private final OpenCommentService commentService;

    @GetMapping
    public List<CommentDto> getAllByEvent(@RequestParam int eventId,
                                          @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                          @RequestParam(defaultValue = "10") @Positive int size) {
        log.debug(LogMessages.TRY_PUBLIC_GET_COMMENTS.label);
        return commentService.getAllByEvent(eventId, from, size);
    }
}