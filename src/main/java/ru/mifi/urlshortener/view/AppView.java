package ru.mifi.urlshortener.view;

import ru.mifi.urlshortener.model.Link;
import ru.mifi.urlshortener.model.User;
import ru.mifi.urlshortener.service.LinkService;
import ru.mifi.urlshortener.service.UserService;

import java.awt.Desktop;
import java.net.URI;
import java.util.Scanner;

public class AppView {

    private final UserService userService = new UserService();
    private final LinkService linkService = new LinkService();
    private User currentUser;

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to URL Shortener!");

        currentUser = userService.createUser();
        System.out.println("Your User ID: " + currentUser.getId());

        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Create a short link");
            System.out.println("2. List all links");
            System.out.println("3. Open a link");
            System.out.println("4. Delete a link");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine();

            // Проверка ввода
            if (!isValidMenuOption(input)) {
                System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                continue;
            }

            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1 -> createLink(scanner);
                case 2 -> listLinks();
                case 3 -> openLink(scanner);
                case 4 -> deleteLink(scanner);
                case 5 -> {
                    System.out.println("Exiting... Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Метод проверки корректности выбора пункта меню
    private boolean isValidMenuOption(String input) {
        try {
            int choice = Integer.parseInt(input);
            return choice >= 1 && choice <= 5;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void createLink(Scanner scanner) {
        System.out.print("Enter original URL: ");
        String originalUrl = scanner.nextLine();

        // Проверка и добавление схемы, если отсутствует
        if (!originalUrl.startsWith("http://") && !originalUrl.startsWith("https://")) {
            originalUrl = "http://" + originalUrl;
        }

        int ttl = getValidatedInteger(scanner, "Enter time-to-live (hours): ");
        int maxTransitions = getValidatedInteger(scanner, "Enter maximum transitions: ");

        Link link = linkService.createLink(originalUrl, ttl, maxTransitions, currentUser.getId().toString());
        System.out.println("Short URL created: clck.ru/" + link.getId());
    }

    // Метод для проверки ввода целых чисел
    private int getValidatedInteger(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid integer.");
            }
        }
    }

    private void listLinks() {
        System.out.println("Your links:");
        linkService.getLinks().values().stream()
                .filter(link -> link.getOwnerId().equals(currentUser.getId()))
                .forEach(link -> System.out.println(link.getId() + " -> " + link.getOriginalUrl() + " [Transitions: " +
                        link.getTransitionCount() + "/" + link.getMaxTransitions() + "]"));
    }

    private void openLink(Scanner scanner) {
        System.out.print("Enter short URL ID: ");
        String id = scanner.nextLine();
        try {
            String originalUrl = linkService.openLink(id);
            System.out.println("Opening " + originalUrl);
            Desktop.getDesktop().browse(new URI(originalUrl));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteLink(Scanner scanner) {
        System.out.print("Enter short URL ID: ");
        String id = scanner.nextLine();
        try {
            linkService.deleteLink(id, currentUser.getId().toString());
            System.out.println("Link deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
