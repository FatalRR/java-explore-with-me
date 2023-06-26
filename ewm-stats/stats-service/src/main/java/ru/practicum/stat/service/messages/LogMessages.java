package ru.practicum.stat.service.messages;

public enum LogMessages {
    TRY_POST_HIT("Попытка добавить {}"),
    TRY_GET_STATS("Попытка получить start {},  end {} uris {}  unique {}"),
    POST_HIT("Записи статистики успешно добавлены."),
    GET_STATS("Записи статистики успешно получены.");

    public final String label;

    LogMessages(String label) {
        this.label = label;
    }
}