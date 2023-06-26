package ru.practicum.stat.service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stat.dto.RequestDto;
import ru.practicum.stat.dto.ResponseDto;
import ru.practicum.stat.service.messages.LogMessages;
import ru.practicum.stat.service.service.StatsService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatsController {
    private final StatsService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/hit")
    public ResponseDto hit(@Valid @RequestBody RequestDto requestDto) {
        log.debug(LogMessages.TRY_POST_HIT.label, requestDto);
        return service.hit(requestDto);
    }

    @GetMapping(path = "/stats")
    public List<ResponseDto> stats(@RequestParam(name = "start") String start,
                                   @RequestParam(name = "end") String end,
                                   @RequestParam(name = "uris", required = false) List<String> uris,
                                   @RequestParam(name = "unique", required = false, defaultValue = "false") boolean unique) {
        log.debug(LogMessages.TRY_GET_STATS.label, start, end, uris, unique);
        return service.stats(start, end, uris, unique);
    }
}