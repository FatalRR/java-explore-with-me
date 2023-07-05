package ru.practicum.main.stat.service.service;

import ru.practicum.main.stat.dto.RequestDto;
import ru.practicum.main.stat.dto.ResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    ResponseDto hit(RequestDto requestDto);

    List<ResponseDto> stats(LocalDateTime start, LocalDateTime end, List<String> uri, boolean unique);
}