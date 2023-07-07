package ru.practicum.main.events.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.events.admin.service.AdminEventsService;
import ru.practicum.main.events.dto.AdminEventRequests;
import ru.practicum.main.events.dto.EventFullDto;
import ru.practicum.main.events.dto.UpdateEventAdminRequest;
import ru.practicum.main.messages.LogMessages;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AdminEventsController {
    private final AdminEventsService service;

    @GetMapping(path = "/events")
    public List<EventFullDto> findEvents(@RequestParam(name = "users", required = false) List<Integer> users,
                                         @RequestParam(name = "states", required = false) List<String> states,
                                         @RequestParam(name = "categories", required = false) List<Integer> categories,
                                         @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                         @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                         @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
                                         @RequestParam(name = "size", defaultValue = "10") @Positive int size) {
        log.debug(LogMessages.TRY_ADMIN_GET_EVENT.label);
        return service.findEvents(AdminEventRequests.of(users, states, categories, rangeStart, rangeEnd, from, size));
    }

    @PatchMapping(path = "/events/{eventId}")
    public EventFullDto changeEvents(@PathVariable(name = "eventId") @Positive int eventId,
                                     @RequestBody @Valid UpdateEventAdminRequest updateEventAdminRequest) {
        log.debug(LogMessages.TRY_ADMIN_PATCH_EVENT_ID.label, eventId);
        return service.changeEvents(eventId, updateEventAdminRequest);
    }
}