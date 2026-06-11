# Vikings Raid Planner

Планировщик набегов викингов.

## Стек технологий
- Java 17/21
- Spring Boot 3.1.5
- Spring Security
- Spring Data JPA
- PostgreSQL
- Maven
- HTML/CSS/JavaScript

## Инструкция запуска

### Шаг 1: Установите Java 21
- Скачайте JDK 21: https://adoptium.net/
- Установите на компьютер

### Шаг 2: Установите PostgreSQL
- Скачайте PostgreSQL: https://www.postgresql.org/download/windows/
- Установите (запомните пароль от пользователя postgres)

### Шаг 3: Создайте базу данных
- Откройте pgAdmin или командную строку
- Выполните SQL:
```sql
CREATE DATABASE vikings_planner;
```
### Шаг 4: Настройте подключение к БД
- Откройте файл application.yml
- Найдите строки:
```yaml
  url: jdbc:postgresql://localhost:5432/vikings_planner
  username: postgres
  password: postgres-14
```
- Измените password на ваш пароль от PostgreSQL

### Шаг 5: Запустите приложение
- Способ 1:
   run.bat
- Способ 2:
  Откройте командную строку и выполните:
```bash
java -jar target\planner-1.0-SNAPSHOT.jar
```
### Шаг 6: Откройте браузер
- http://localhost:8080/index.html

### Шаг 7: Зарегистрируйтесь и войдите
- Введите username и пароль
- Нажмите "Register"
- Введите логин и пароль и нажмите "Login"

### Требования
- JDK 21
- PostgreSQL 14+

## Тест-кейсы

### TC-01: Регистрация
1. Открыть http://localhost:8080/index.html
2. Ввести username и password в форме Register
3. Нажать Register
4. Ожидаемый результат: "Registration successful"

### TC-02: Логин
1. Ввести данные в форме Login
2. Нажать Login
3. Ожидаемый результат: открывается главное меню

### TC-03: Успешный рейд
1. Выбрать корабль Drakkar
2. Выбрать 6+ викингов
3. Выбрать поселение Lindisfarne
4. Нажать START RAID
5. Ожидаемый результат: "RAID SUCCESSFUL" или "RAID FAILED - No successful raids"

### TC-04: Провал рейда
1. Выбрать корабль
2. Выбрать 1 воина
3. Выбрать Constantinople
4. Нажать START RAID
5. Ожидаемый результат: "RAID FAILED - Not enough supplies"

## Скриншоты приложены отдельно в виде файлов
### 1. Страница регистрации и логина
![Регистрация и логин](screenshot1.png)

### 2. Главное меню с выбранными параметрами
![Выбор корабля, команды, маршрута](screenshot2.png)
![Выбор корабля, команды, маршрута](screenshot3.png)

### 3. Результат рейда
![Результат симуляции](screenshot3.png)
