package ru.mifi.urlshortener.service;

import lombok.Data;
import ru.mifi.urlshortener.model.Link;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class LinkService {

    private final Map<String, Link> links = new HashMap<>();

    // Создание короткой ссылки
    public Link createLink(String originalUrl, int ttlHours, int maxTransitions, String userId) {
        String shortId = generateShortId();
        Link link = Link.builder()
                .id(shortId)
                .originalUrl(originalUrl)
                .creationTime(LocalDateTime.now())
                .expirationTime(LocalDateTime.now().plusHours(ttlHours))
                .maxTransitions(maxTransitions)
                .transitionCount(0)
                .ownerId(UUID.fromString(userId))
                .build();
        links.put(shortId, link);
        return link;
    }

    // Получение ссылки по короткому ID
    public Link getLink(String id) {
        return links.get(id);
    }

    // Удаление ссылки
    public void deleteLink(String id, String userId) {
        Link link = links.get(id);
        if (link == null || !link.getOwnerId().toString().equals(userId)) {
            throw new IllegalArgumentException("You do not have permission to delete this link.");
        }
        links.remove(id);
    }

    // Переход по ссылке
    public String openLink(String id) {
        Link link = links.get(id);
        if (link == null) {
            throw new IllegalArgumentException("Link not found.");
        }
        if (LocalDateTime.now().isAfter(link.getExpirationTime()) || link.getTransitionCount() >= link.getMaxTransitions()) {
            throw new IllegalArgumentException("Link expired or limit reached.");
        }
        link.setTransitionCount(link.getTransitionCount() + 1);
        return link.getOriginalUrl();
    }

    // Генерация короткого ID
    private String generateShortId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }


    public Map<String, Link> getLinks() {
        return links;
    }
}
