package ru.practicum.main.categories.open.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.categories.dto.CategoryDto;
import ru.practicum.main.categories.open.service.OpenCategoriesService;
import ru.practicum.main.messages.LogMessages;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class OpenController {
    private final OpenCategoriesService service;

    @GetMapping(path = "/categories")
    public List<CategoryDto> getCategories(@RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
                                           @RequestParam(name = "size", defaultValue = "10") @Positive int size) {
        log.debug(LogMessages.PUBLIC_GET_CATEGORIES.label);
        return service.getCategories(from, size);
    }

    @GetMapping(path = "/categories/{catId}")
    public CategoryDto getCategoriesById(@PathVariable(name = "catId") @Positive int catId) {
        log.debug(LogMessages.PUBLIC_GET_CATEGORIES_ID.label, catId);
        return service.getCategoriesById(catId);
    }
}