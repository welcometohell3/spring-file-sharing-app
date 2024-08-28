# File Sharing Application

## Описание проекта

Это приложение для обмена файлами позволяет пользователям загружать, скачивать, делиться и удалять файлы. Пользователи могут регистрироваться, входить в систему, а также управлять своими файлами.

## Стек технологий

- **Бэкенд:**
  - Java 17
  - Spring Boot
  - Spring Security
  - PostgreSQL
  - Maven

- **Фронтенд:**
  - React
  - Semantic UI
  - Axios

- **Инфраструктура:**
  - Docker
  - Docker Compose

## Установка и запуск

1. Клонируйте репозиторий:

    ```bash
    git clone git@github.com:welcometohell3/spring-file-sharing-app.git
    ```

2. Перейдите в корневую директорию проекта.

    ```bash
   cd spring-file-sharing-app
    ```

3. Запустите Docker Compose:

    ```bash
    docker compose up --build
    ```

## Примеры использования

### Вход в систему

1. Откройте веб-приложение.
2. Перейдите на страницу входа в систему.
3. Введите имя пользователя и пароль.
4. Нажмите кнопку "Войти".

### Загрузка файла

1. Войдите в систему.
2. Перейдите на страницу загрузки файла.
3. Выберите файл для загрузки.
4. Нажмите кнопку "Загрузить".