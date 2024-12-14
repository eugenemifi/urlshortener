package ru.mifi.urlshortener.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mifi.urlshortener.model.User;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void givenValidRequest_whenCreateUser_thenUserIsCreated() {
        // When
        User user = userService.createUser();

        // Then
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(user, userService.getUser(user.getId().toString()));
    }

    @Test
    void givenInvalidId_whenGetUser_thenReturnsNull() {
        // When
        User user = userService.getUser("non-existent-id");

        // Then
        assertNull(user);
    }
}
