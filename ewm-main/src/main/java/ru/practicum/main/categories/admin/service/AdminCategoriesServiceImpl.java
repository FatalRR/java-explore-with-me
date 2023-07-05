package ru.practicum.main.categories.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.categories.dto.CategoryDto;
import ru.practicum.main.categories.dto.NewCategoryDto;
import ru.practicum.main.categories.model.Category;
import ru.practicum.main.categories.repository.CategoriesRepository;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.messages.ExceptionMessages;
import ru.practicum.main.messages.LogMessages;

import static ru.practicum.main.categories.mapper.CategoryMapper.mapToCategory;
import static ru.practicum.main.categories.mapper.CategoryMapper.mapToCategoryDto;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AdminCategoriesServiceImpl implements AdminCategoriesService {
    private final CategoriesRepository repository;

    @Override
    public CategoryDto createCategories(NewCategoryDto newCategoryDto) {
        log.debug(LogMessages.ADMIN_POST_CATEGORIES.label);
        return mapToCategoryDto(repository.save(mapToCategory(newCategoryDto)));
    }

    @Override
    public void deleteCategories(int catId) {
        repository.findById(catId).orElseThrow(() -> new NotFoundException(ExceptionMessages.NOT_FOUND_CATEGORIES_EXCEPTION.label));
        repository.deleteById(catId);
    }

    @Override
    public CategoryDto changeCategories(int catId, CategoryDto categoryDto) {
        Category oldCategory = repository.findById(catId).orElseThrow();
        if (categoryDto.getName() != null) {
            oldCategory.setName(categoryDto.getName());
        }
        log.debug(LogMessages.ADMIN_PATCH_CATEGORIES_ID.label, catId);
        return mapToCategoryDto(repository.save(oldCategory));
    }

    @Transactional(readOnly = true)
    @Override
    public Category findCategoriesById(int catId) {
        return repository.findById(catId).orElseThrow(() -> new NotFoundException(ExceptionMessages.NOT_FOUND_CATEGORIES_EXCEPTION.label));
    }
}