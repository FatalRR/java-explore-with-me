package ru.practicum.main.compilations.open.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.compilations.dto.CompilationDto;
import ru.practicum.main.compilations.repository.CompilationsRepository;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.messages.ExceptionMessages;
import ru.practicum.main.messages.LogMessages;

import java.util.List;

import ru.practicum.main.compilations.mapper.CompilationMapper;
import ru.practicum.main.utils.Page;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OpenCompilationsServiceImpl implements OpenCompilationsService {
    private final CompilationsRepository repository;

    @Override
    public List<CompilationDto> getCompilations(String pinned, int from, int size) {
        Pageable page = Page.paged(from, size);
        log.debug(LogMessages.PUBLIC_GET_COMPILATIONS.label);
        return CompilationMapper.mapToListCompilationsDto(repository.findCompilationsByPinnedIs(Boolean.parseBoolean(pinned), page));
    }

    @Override
    public CompilationDto getCompilationsById(int compId) {
        log.debug(LogMessages.PUBLIC_GET_COMPILATIONS_ID.label, compId);
        return CompilationMapper.mapToCompilationsDto(repository.findById(compId).orElseThrow(()
                -> new NotFoundException(ExceptionMessages.NOT_FOUND_COMPILATIONS_EXCEPTION.label)));
    }
}