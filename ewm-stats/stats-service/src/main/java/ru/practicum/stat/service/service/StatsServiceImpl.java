package ru.practicum.stat.service.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stat.dto.RequestDto;
import ru.practicum.stat.dto.ResponseDto;
import ru.practicum.stat.service.exception.NotStatException;
import ru.practicum.stat.service.exception.TimestampException;
import ru.practicum.stat.service.mapper.StatsMapper;
import ru.practicum.stat.service.messages.ExceptionMessages;
import ru.practicum.stat.service.messages.LogMessages;
import ru.practicum.stat.service.model.Stats;
import ru.practicum.stat.service.repository.StatsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@NoArgsConstructor
public class StatsServiceImpl implements StatsService {
    private StatsRepository statsRepository;

    @Autowired
    public StatsServiceImpl(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    @Override
    public ResponseDto hit(RequestDto requestDto) {
        Stats state = StatsMapper.mapToStat(requestDto);
        state.setTimestamp(LocalDateTime.now());
        log.debug(LogMessages.POST_HIT.label);
        return StatsMapper.mapToResponseDto(statsRepository.save(state));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ResponseDto> stats(String start, String end, List<String> uri, boolean unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime timeStart = LocalDateTime.parse(start, formatter);
        LocalDateTime timeEnd = LocalDateTime.parse(end, formatter);
        List<ResponseDto> listResponseStat = new ArrayList<>();

        if (timeStart.isAfter(timeEnd)) {
            throw new TimestampException(ExceptionMessages.START_IS_AFTER_END.label);
        }

        if (uri != null && !uri.isEmpty()) {
            if (unique) {
                for (String u : uri) {
                    listResponseStat.addAll(statsRepository.findStatUriUnique(timeStart, timeEnd, u));
                }
            } else {
                for (String u : uri) {
                    listResponseStat.addAll(statsRepository.findStatUri(timeStart, timeEnd, u));
                }
            }
        } else {
            if (unique) {
                listResponseStat.addAll(statsRepository.findStatUnique(timeStart, timeEnd));
            } else {
                listResponseStat.addAll(statsRepository.findStat(timeStart, timeEnd));
            }
        }

        if (listResponseStat.isEmpty()) {
            throw new NotStatException(ExceptionMessages.NOT_FOUND_STATE.label);
        } else {
            log.debug(LogMessages.GET_STATS.label);
            return listResponseStat.stream().sorted(Comparator.comparing(ResponseDto::getHits).reversed()).collect(Collectors.toList());
        }
    }
}