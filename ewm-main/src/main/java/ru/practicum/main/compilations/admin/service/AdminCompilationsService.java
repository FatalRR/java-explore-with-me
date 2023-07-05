package ru.practicum.main.compilations.admin.service;

import ru.practicum.main.compilations.dto.CompilationDto;
import ru.practicum.main.compilations.dto.NewCompilationDto;
import ru.practicum.main.compilations.dto.UpdateCompilationRequest;

public interface AdminCompilationsService {
    CompilationDto createCompilations(NewCompilationDto newCompilationDto);

    void deleteCompilations(int compId);

    CompilationDto changeCompilations(int compId, UpdateCompilationRequest updateCompilationRequest);
}