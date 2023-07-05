package ru.practicum.main.stat.service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.stat.dto.RequestDto;
import ru.practicum.main.stat.dto.ResponseDto;
import ru.practicum.main.stat.service.exception.NotStatException;
import ru.practicum.main.stat.service.exception.TimestampException;
import ru.practicum.main.stat.service.messages.ExceptionMessages;
import ru.practicum.main.stat.service.messages.LogMessages;
import ru.practicum.main.stat.service.repository.StatsRepository;
import ru.practicum.main.stat.service.mapper.StatsMapper;
import ru.practicum.main.stat.service.model.Stats;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    @Override
    public ResponseDto hit(RequestDto requestDto) {
        Stats state = StatsMapper.mapToStat(requestDto);
        state.setTimestamp(LocalDateTime.now());
        log.debug(LogMessages.POST_HIT.label);
        return StatsMapper.mapToResponseDto(statsRepository.save(state));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ResponseDto> stats(LocalDateTime start, LocalDateTime end, List<String> uri, boolean unique) {
        List<ResponseDto> listResponseStat = new ArrayList<>();

        if (start.isAfter(end)) {
            throw new TimestampException(ExceptionMessages.START_IS_AFTER_END.label);
        }

        if (uri != null && !uri.isEmpty()) {
            if (unique) {
                for (String u : uri) {
                    if (u.contains("[")) {
                        u = u.substring(u.indexOf('/'), u.indexOf(']'));
                    }
                    listResponseStat.addAll(statsRepository.findStatUriUnique(start, end, u));
                }
            } else {
                for (String u : uri) {
                    if (u.contains("[")) {
                        u = u.substring(u.indexOf('/'), u.indexOf(']'));
                    }
                    listResponseStat.addAll(statsRepository.findStatUri(start, end, u));
                }
            }
        } else {
            if (unique) {
                listResponseStat.addAll(statsRepository.findStatUnique(start, end));
            } else {
                listResponseStat.addAll(statsRepository.findStat(start, end));
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