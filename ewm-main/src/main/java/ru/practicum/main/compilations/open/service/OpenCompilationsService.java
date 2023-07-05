package ru.practicum.main.compilations.open.service;

import ru.practicum.main.compilations.dto.CompilationDto;

import java.util.List;

public interface OpenCompilationsService {
    List<CompilationDto> getCompilations(String pinned, int from, int size);

    CompilationDto getCompilationsById(int compId);
}