# Дипломный проект по профессии «Тестировщик»

[Задание по Дипломной работе](https://github.com/netology-code/qa-diploma)

## Описание приложения

#### Бизнес-часть

Это приложение выглядит как веб-сервис, который предлагает купить тур по определённой цене двумя способами:

1. Обычная оплата по дебетовой карте
2. Уникальная технология: выдача кредита по данным банковской карты

![service.png](pic/service.png)

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:

- сервису платежей, далее Payment Gate;
- кредитному сервису, далее Credit Gate.

Приложение в собственной СУБД должно сохранять информацию о том, успешно ли был совершён платёж и каким способом. Данные карт при этом сохранять не допускается.

### Перечень используемых программ для реализации тестов

##### Необходимо установить на ПК:

**1**. IntelliJ IDEA среда разработки программного обеспечения для написания тестов.

**2**. Docker - система контейнерезации. Позволит подключить базы данных MySQL и PostgresSQL.

**3**. DBeaver или Valentina Studio 14 - приложение для управления базами данных, необходимо для просмотра содержимого таблиц в базах данных;

**4**. Allure - фреймворк, предназначенный для создания визуально наглядной картины выполнения тестов.

**5**. Git - Применяется для управления версиями, хранение кода, в том числе автотестов.

**6** Googl Chrome - веб-браузер для просматривания отчетов Allure.



 ### Процеcc запуска тестов:

**1**. Склонировать репозиторий [Diplom-QA](https://github.com/Katkutia/Dilpom-QA/)на своё устройство.

**2**. Запустить программу Docker Desktop.

**3**. Открыть склонированый проект в программе IntelliJ IDEA.

**4**. Далее нам необходимо запустить контейнеры, в терминале IntelliJ IDEA вводим команду
   - ```docker compose up ```

**5**. Для запуска PostgreSQL в терминале вводим команду:
   - ```java -jar ./artifacts/aqa-shop.jar "--spring.datasource.url=jdbc:postgresql://localhost:5432/app"``` 

**6**. Для запуска MySQL в терминале вводим команду
   - ```java -jar ./artifacts/aqa-shop.jar "--spring.datasource.url=jdbc:mysql://localhost:3306/app"```

**7**. Для запуска тестов PostgreSQL во втором терминале вводим команду:
   - ```./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app" ``` 

**8**. Для запуска тестов MySQL во втором терминале вводим команду:
   - ```./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app" ```

**9**. Запуск для создания отчетов Allure gradlew командой
   - ```./gradlew allureReport```

**10** Создаем отчет в браузере Allure gradlew командой
   - ```./gradlew allureServe```

**11**. После завершения тестирования, остановливаем контейнер командой 
   - ```docker-compose down```

