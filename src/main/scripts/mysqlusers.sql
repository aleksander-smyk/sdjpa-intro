-- Ustawienie kodowania dla sesji
SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;
SET CHARACTER SET utf8mb4;

-- Usunięcie istniejących obiektów
DROP DATABASE IF EXISTS bookdb;
DROP USER IF EXISTS `bookadmin`@`%`;
DROP USER IF EXISTS `bookuser`@`%`;

-- Utworzenie bazy danych z pełnym wsparciem dla polskich znaków
CREATE DATABASE IF NOT EXISTS bookdb
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- Użycie utworzonej bazy danych
USE bookdb;

-- Utworzenie użytkowników z prawidłowym kodowaniem
CREATE USER IF NOT EXISTS `bookadmin`@`%` IDENTIFIED BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW,
    CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `bookdb`.* TO `bookadmin`@`%`;

CREATE USER IF NOT EXISTS `bookuser`@`%` IDENTIFIED BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, SHOW VIEW ON `bookdb`.* TO `bookuser`@`%`;

-- Wymuszenie odświeżenia uprawnień
FLUSH PRIVILEGES;

-- Sprawdzenie kodowania bazy danych
SELECT
    SCHEMA_NAME as 'Database',
    DEFAULT_CHARACTER_SET_NAME as 'Charset',
    DEFAULT_COLLATION_NAME as 'Collation'
FROM information_schema.SCHEMATA
WHERE SCHEMA_NAME = 'bookdb';

-- Komentarz: utf8mb4 obsługuje pełny zakres Unicode,
-- w tym polskie znaki diakrytyczne (ą, ć, ę, ł, ń, ó, ś, ź, ż)