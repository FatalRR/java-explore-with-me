package ru.practicum.main.stat.service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.stat.dto.ResponseDto;
import ru.practicum.main.stat.dto.RequestDto;
import ru.practicum.main.stat.service.messages.LogMessages;
import ru.practicum.main.stat.service.service.StatsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {
    private final StatsService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/hit")
    public ResponseDto hit(@Valid @RequestBody RequestDto requestDto) {
        log.debug(LogMessages.TRY_POST_HIT.label, requestDto);
        return service.hit(requestDto);
    }

    @GetMapping(path = "/stats")
    public List<ResponseDto> stats(@RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                   @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                   @RequestParam(name = "uris", required = false) List<String> uris,
                                   @RequestParam(name = "unique", defaultValue = "false") boolean unique) {
        log.debug(LogMessages.TRY_GET_STATS.label, start, end, uris, unique);
        return service.stats(start, end, uris, unique);
    }
}