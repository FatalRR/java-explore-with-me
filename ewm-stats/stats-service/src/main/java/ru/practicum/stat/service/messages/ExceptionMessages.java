package ru.practicum.stat.service.messages;

public enum ExceptionMessages {
    START_IS_AFTER_END("Время начала диапазона поиска не может быть позже времени конца."),
    NOT_FOUND_STATE("Записи статистики не найдены.");

    public final String label;

    ExceptionMessages(String label) {
        this.label = label;
    }
}