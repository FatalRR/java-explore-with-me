package ru.practicum.main.categories.open.service;

import ru.practicum.main.categories.dto.CategoryDto;
import ru.practicum.main.categories.model.Category;

import java.util.List;

public interface OpenCategoriesService {
    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategoriesById(int catId);

    Category getCatById(int catId);
}