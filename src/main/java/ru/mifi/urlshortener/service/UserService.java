package ru.mifi.urlshortener.service;

import ru.mifi.urlshortener.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserService {

    private final Map<String, User> users = new HashMap<>();

    // Создание нового пользователя
    public User createUser() {
        String id = UUID.randomUUID().toString();
        User user = User.builder()
                .id(UUID.fromString(id))
                .build();
        users.put(id, user);
        return user;
    }

    // Получение пользователя
    public User getUser(String id) {
        return users.get(id);
    }

    public Map<String, User> getUsers() {
        return users;
    }
}
