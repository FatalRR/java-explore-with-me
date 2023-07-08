package ru.practicum.main.categories.open.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.categories.dto.CategoryDto;
import ru.practicum.main.categories.model.Category;
import ru.practicum.main.categories.repository.CategoriesRepository;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.messages.ExceptionMessages;
import ru.practicum.main.messages.LogMessages;

import java.util.List;

import ru.practicum.main.categories.mapper.CategoryMapper;

import static ru.practicum.main.utils.Page.paged;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OpenCategoriesServiceImpl implements OpenCategoriesService {
    private final CategoriesRepository repository;

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        Pageable page = paged(from, size);
        log.debug(LogMessages.PUBLIC_GET_CATEGORIES.label);
        return CategoryMapper.mapToListCategoryDto(repository.findAll(page));
    }

    @Override
    public CategoryDto getCategoriesById(int catId) {
        log.debug(LogMessages.PUBLIC_GET_CATEGORIES_ID.label, catId);
        return CategoryMapper.mapToCategoryDto(repository.findById(catId).orElseThrow(() -> new NotFoundException(ExceptionMessages.NOT_FOUND_CATEGORIES_EXCEPTION.label)));
    }

    @Override
    public Category getCatById(int catId) {
        return repository.findById(catId).orElseThrow(() -> new NotFoundException(ExceptionMessages.NOT_FOUND_COMPILATIONS_EXCEPTION.label));
    }
}