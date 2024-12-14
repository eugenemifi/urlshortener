package ru.mifi.urlshortener.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Link {
    private String id;
    private String originalUrl;
    private LocalDateTime creationTime;
    private LocalDateTime expirationTime;
    private int maxTransitions;
    private int transitionCount;
    private UUID ownerId;
}
