# URL Shortener

![Version](https://img.shields.io/badge/Version-1.0-blue)
![Java](https://img.shields.io/badge/Java-17-green)
![Gradle](https://img.shields.io/badge/Gradle-BuildTool-orange)

## 📖 Описание

**URL Shortener** – это консольный сервис для сокращения длинных URL с дополнительными функциями:
- Управление сроком жизни ссылок.
- Установка лимита переходов.
- Поддержка нескольких пользователей.
- Уведомления о недоступности ссылок.
- Уникальная идентификация пользователей с помощью UUID.

Проект разработан для управления ссылками с удобным интерфейсом и возможностью редактирования параметров.

---

## 🚀 Основные функции

- **Создание уникальных коротких ссылок для каждого пользователя.**
- **Ограничение по времени жизни.**
    - Ссылки автоматически удаляются по истечении времени жизни.
- **Установка лимита переходов.**
    - Ссылки блокируются после достижения указанного лимита переходов.
- **Уведомления.**
    - Уведомление пользователя при истечении срока жизни ссылки или достижении лимита.
- **Управление ссылками.**
    - Редактирование лимита переходов.
    - Удаление ссылок пользователем.
- **Многопользовательская поддержка.**
    - У каждого пользователя свой набор ссылок.
- **Переход по ссылкам через консоль.**

---

## 🛠️ Технологии

- **Java 17** – основной язык разработки.
- **Gradle** – инструмент сборки.
- **JUnit 5** – тестирование.

---

## 📋 Требования

- Установленная **Java Development Kit (JDK)** версии 17 или выше.
- Инструмент сборки **Gradle** (или IDE, поддерживающая Gradle, например IntelliJ IDEA).

---

## 📦 Установка и запуск

### 1. Клонирование репозитория
```bash
git clone https://github.com/username/url-shortener.git
cd url-shortener
```

### 2. Сборка проекта
```bash
./gradlew build
```

### 3. Запуск приложения
```bash
./gradlew run
```

📖 Руководство пользователя
Команды приложения:
1. Создание короткой ссылки
В главном меню выберите опцию Create a short link, введите:
Длинный URL.
Время жизни ссылки (в часах).
Максимальное количество переходов.

Пример:
```bash
Enter original URL: https://www.example.com
Enter time-to-live (hours): 24
Enter maximum transitions: 100
Short URL created: clck.ru/abcd123
```

2. Просмотр всех ссылок
   Выберите опцию List all links для отображения всех ссылок пользователя, их параметров, оставшегося времени жизни и лимита переходов.
```text
Your links:
clck.ru/abcd123 -> https://www.example.com [Transitions: 10/100]
```

3. Переход по ссылке
   Выберите опцию Open a link, введите короткий URL, и браузер откроет исходный ресурс.
```text
Enter short URL ID: abcd123
Opening https://www.example.com...
```

4. Удаление ссылки
   Выберите опцию Delete a link, введите ID короткой ссылки, чтобы удалить её.

Пример:
```text
Enter short URL ID: abcd123
Link deleted successfully.
```

🧪 Тестирование

1. Запуск тестов:
```text
./gradlew test
```
2. Отчет о тестах: После завершения тестов отчет доступен по пути:
```text
build/reports/tests/test/index.html
```

📂 Структура проекта
```text
src
├── main
│   ├── java
│   │   ├── ru.mifi.urlshortener
│   │   │   ├── model        # Модели данных: Link, User
│   │   │   ├── service      # Сервисы: LinkService, UserService
│   │   │   ├── view         # Консольный интерфейс: ConsoleApp
│   │   └── AppRunner.java   # Точка входа
└── test
    ├── java
        ├── LinkServiceTest.java
        ├── UserServiceTest.java
```

🌟 Пример работы приложения

Создание короткой ссылки
```text
Welcome to URL Shortener!

Main Menu:
1. Create a short link
2. List all links
3. Open a link
4. Delete a link
5. Exit
Enter your choice: 1

Enter original URL: https://www.example.com
Enter time-to-live (hours): 24
Enter maximum transitions: 50
Short URL created: clck.ru/abcd123
```
Переход по короткой ссылке

```text
Main Menu:
1. Create a short link
2. List all links
3. Open a link
4. Delete a link
5. Exit
Enter your choice: 3

Enter short URL ID: abcd123
Opening https://www.example.com...
```

