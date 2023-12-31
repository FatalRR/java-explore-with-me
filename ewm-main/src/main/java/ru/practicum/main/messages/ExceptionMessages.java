package ru.practicum.main.messages;

public enum ExceptionMessages {
    NOT_FOUND_EXCEPTION("Объект не найден или недоступен."),
    NOT_FOUND_CATEGORIES_EXCEPTION("Категория не найдена или недоступна."),
    NOT_FOUND_COMPILATIONS_EXCEPTION("Подборка не найдена или недоступна."),
    NOT_FOUND_EVENTS_EXCEPTION("Событие не найдено или недоступно."),
    NOT_FOUND_REQUESTS_EXCEPTION("Запрос не найден или недоступен."),
    NOT_FOUND_USERS_EXCEPTION("Пользователь не найден или недоступен."),
    NOT_FOUND_COMMENT_EXCEPTION("Комментарий не найден или недоступен."),
    CONFLICT_EXCEPTION("Нарушение целостности данных."),
    VALID_TIME_EXCEPTION("Данные не удовлетворяет правилам создания."),
    INVALID_PAGE_PARAMETERS("Неправильные параметры страницы."),
    INTERNAL_SERVER_ERROR("Ошибка сервера"),
    MISSING_REQUEST_PARAM("Отсутствуют параметры запроса");

    public final String label;

    ExceptionMessages(String label) {
        this.label = label;
    }
}