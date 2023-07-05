package ru.practicum.main.compilations.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.compilations.dto.CompilationDto;
import ru.practicum.main.compilations.dto.NewCompilationDto;
import ru.practicum.main.compilations.dto.UpdateCompilationRequest;
import ru.practicum.main.compilations.model.Compilations;
import ru.practicum.main.compilations.repository.CompilationsRepository;
import ru.practicum.main.events.close.service.CloseEventsService;
import ru.practicum.main.events.model.Event;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.messages.ExceptionMessages;
import ru.practicum.main.messages.LogMessages;

import java.util.ArrayList;
import java.util.List;

import ru.practicum.main.compilations.mapper.CompilationMapper;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AdminCompilationsServiceImpl implements AdminCompilationsService {
    private final CompilationsRepository compilationsRepository;
    private final CloseEventsService eventsService;

    @Override
    public CompilationDto createCompilations(NewCompilationDto newCompilationDto) {
        List<Event> eventList = new ArrayList<>();

        if (newCompilationDto.getEvents() != null && !newCompilationDto.getEvents().isEmpty()) {
            for (Integer eventId : newCompilationDto.getEvents()) {
                eventList.add(eventsService.getEventById(eventId));
            }
        }
        log.debug(LogMessages.ADMIN_POST_COMPILATIONS.label);
        return CompilationMapper.mapToCompilationsDto(compilationsRepository.save(CompilationMapper.mapToCompilations(newCompilationDto, eventList)));
    }

    @Override
    public void deleteCompilations(int compId) {
        compilationsRepository.findById(compId).orElseThrow(() -> new NotFoundException(ExceptionMessages.NOT_FOUND_COMPILATIONS_EXCEPTION.label));
        log.debug(LogMessages.ADMIN_DELETE_COMPILATIONS_ID.label, compId);
        compilationsRepository.deleteById(compId);
    }

    @Override
    public CompilationDto changeCompilations(int compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilations oldCompilations = compilationsRepository.findById(compId).orElseThrow(() -> new NotFoundException(ExceptionMessages.NOT_FOUND_COMPILATIONS_EXCEPTION.label));

        if (updateCompilationRequest.getEvents() != null && !updateCompilationRequest.getEvents().isEmpty()) {
            List<Event> eventList = new ArrayList<>();

            for (Integer eventId : updateCompilationRequest.getEvents()) {
                eventList.add(eventsService.getEventById(eventId));
            }

            oldCompilations.setEventsWithCompilations(eventList);
        }
        if (updateCompilationRequest.getPinned() != null) {
            oldCompilations.setPinned(updateCompilationRequest.getPinned());
        }
        if (updateCompilationRequest.getTitle() != null && !updateCompilationRequest.getTitle().isEmpty()) {
            oldCompilations.setTitle(updateCompilationRequest.getTitle());
        }

        log.debug(LogMessages.ADMIN_PATCH_COMPILATIONS_ID.label, compId);
        return CompilationMapper.mapToCompilationsDto(compilationsRepository.save(oldCompilations));
    }
}