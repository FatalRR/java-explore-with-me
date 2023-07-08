package ru.practicum.main.categories.admin.service;

import ru.practicum.main.categories.dto.CategoryDto;
import ru.practicum.main.categories.dto.NewCategoryDto;
import ru.practicum.main.categories.model.Category;

public interface AdminCategoriesService {
    CategoryDto createCategories(NewCategoryDto newCategoryDto);

    void deleteCategories(int catId);

    CategoryDto changeCategories(int catId, CategoryDto categoryDto);

    Category findCategoriesById(int catId);
}