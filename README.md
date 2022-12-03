# _REST API BACKEND SERVICE "Explore With Me"_
#### Сервис позволяющий делиться информацией об интересных событиях и помогать найти компанию для участия в них
https://github.com/AlSl94/java-explore-with-me/pull/1

### _Используемые технологии_
- Java 11
- SpringBoot Framework
- PostgreSQL
- JPA, Hibernate
- Docker
- Swagger

## _Структура приложения_

### _Gateway_

Проверяет права пользователей
Передает запросы на остальные микросервисы в зависимости прав
Связывает основной сервис и сервис статистики с друг другом

### _Main Service API_
#### Главный сервис, в котором реализованы основные функции приложения
- /users 
- /events  
- /requests
- /compilations
- /categories

### _Statistic server API_
#### Сервис статистики хранящий количество просмотров и позволяющий делать различные выборки для анализа работы приложения.
- /hit
- /stats
- /views

### _Main database_
#### База данных хранящая в себе пользователей, события, подборки, запросы и категории
<img src="https://i.imgur.com/9bDP9Xn.png" width="40%" height="40%">

### _Statistic database_
#### База данных статистики, хранящая в себе количество просмотров событий
<img src="https://i.imgur.com/wK4hUWq.png" width="20%" height="20%">

## _Swagger API Specification_
- Main service - https://raw.githubusercontent.com/AlSl94/java-explore-with-me/main/ewm-main-service-spec.json
- Statistic service - https://raw.githubusercontent.com/AlSl94/java-explore-with-me/main/ewm-stats-service-spec.json

## _Docker start-up guide_
1. mvn clean package
2. mvn install
3. docker-compose build
4. docker-compose up -d
5. Main service: http://localhost:8080
6. Statistic service: http://localhost:9090
