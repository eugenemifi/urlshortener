package ru.mifi.urlshortener.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mifi.urlshortener.model.Link;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LinkServiceTest {

    private LinkService linkService;

    @BeforeEach
    void setUp() {
        linkService = new LinkService();
    }

    @Test
    void givenValidData_whenCreateLink_thenLinkIsCreated() {
        // Given
        String originalUrl = "https://example.com";
        int ttlHours = 24;
        int maxTransitions = 100;
        String userId = "123e4567-e89b-12d3-a456-426614174000";

        // When
        Link link = linkService.createLink(originalUrl, ttlHours, maxTransitions, userId);

        // Then
        assertNotNull(link);
        assertEquals(originalUrl, link.getOriginalUrl());
        assertEquals(maxTransitions, link.getMaxTransitions());
        assertEquals(0, link.getTransitionCount());
        assertEquals(userId, link.getOwnerId().toString());
        assertTrue(link.getExpirationTime().isAfter(LocalDateTime.now()));
    }

    @Test
    void givenValidId_whenGetLink_thenLinkIsReturned() {
        // Given
        String originalUrl = "https://example.com";
        String userId = "123e4567-e89b-12d3-a456-426614174000";
        Link createdLink = linkService.createLink(originalUrl, 24, 100, userId);

        // When
        Link retrievedLink = linkService.getLink(createdLink.getId());

        // Then
        assertNotNull(retrievedLink);
        assertEquals(createdLink, retrievedLink);
    }

    @Test
    void givenValidId_whenOpenLink_thenTransitionCountIsUpdated() {
        // Given
        String originalUrl = "https://example.com";
        String userId = "123e4567-e89b-12d3-a456-426614174000";
        Link link = linkService.createLink(originalUrl, 24, 1, userId);

        // When
        String url = linkService.openLink(link.getId());

        // Then
        assertEquals(originalUrl, url);
        assertEquals(1, link.getTransitionCount());
        assertThrows(IllegalArgumentException.class, () -> linkService.openLink(link.getId()), "Link expired or limit reached.");
    }

    @Test
    void givenExpiredLink_whenOpenLink_thenThrowsException() {
        // Given
        String originalUrl = "https://example.com";
        String userId = "123e4567-e89b-12d3-a456-426614174000";
        Link link = linkService.createLink(originalUrl, -1, 100, userId); // TTL -1 час (сразу истекает)

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> linkService.openLink(link.getId()), "Link expired or limit reached.");
    }

    @Test
    void givenValidId_whenDeleteLink_thenLinkIsDeleted() {
        // Given
        String originalUrl = "https://example.com";
        String userId = "123e4567-e89b-12d3-a456-426614174000";
        Link link = linkService.createLink(originalUrl, 24, 100, userId);

        // When
        linkService.deleteLink(link.getId(), userId);

        // Then
        assertNull(linkService.getLink(link.getId()));
    }
}
