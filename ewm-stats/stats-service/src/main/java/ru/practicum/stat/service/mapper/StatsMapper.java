package ru.practicum.stat.service.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.stat.dto.RequestDto;
import ru.practicum.stat.dto.ResponseDto;
import ru.practicum.stat.service.model.Stats;

@UtilityClass
public class StatsMapper {
    public static Stats mapToStat(RequestDto requestDto) {
        return Stats.builder()
                .app(requestDto.getApp())
                .uri(requestDto.getUri())
                .ip(requestDto.getIp())
                .build();
    }

    public static ResponseDto mapToResponseDto(Stats stats) {
        return ResponseDto.builder()
                .app(stats.getApp())
                .uri(stats.getUri())
                .build();
    }
}