package ru.practicum.stat.service.service;

import ru.practicum.stat.dto.RequestDto;
import ru.practicum.stat.dto.ResponseDto;

import java.util.List;

public interface StatsService {
    ResponseDto hit(RequestDto requestDto);

    List<ResponseDto> stats(String start, String end, List<String> uri, boolean unique);
}