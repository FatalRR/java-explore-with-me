package ru.practicum.main.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.practicum.main.exception.InvalidPageParametersException;
import ru.practicum.main.messages.ExceptionMessages;

@UtilityClass
public class Page {
    public static Pageable paged(Integer from, Integer size) {
        Pageable page;
        if (from != null && size != null) {
            if (from < 0 || size < 0) {
                throw new InvalidPageParametersException(ExceptionMessages.INVALID_PAGE_PARAMETERS.label);
            }
            page = PageRequest.of(from > 0 ? from / size : 0, size);
        } else {
            page = PageRequest.of(0, 4);
        }
        return page;
    }
}