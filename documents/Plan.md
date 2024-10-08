# План автоматизации тестирования

***Цель плана*** - проверка процесса автоматизации тестирования при переходе к форме покупки тура двумя способами:

- При оплате тура дебетовой картой.
- При выдаче кредита по данным банковской картой.

### При проверке нам необходимо заполнить данные разделы:

- Номер карты - допустимы только 16 цифр, прочие символы недопустимы;
- Месяц - двузначное число от 01 до 12, используем текущий месяц текущего года;
- Год - двузначное число, используем текущий год;
- Владелец - имя владельца карты латиницей, например IVAN IVANOV. Недопустимы любые символы, отличные от букв латиницы;
- CVC/CVV - проверочный код из трёх цифр, например 780. Не может состоять из количества цифр, отличного от трёх.

- Нажать кнопку "Купить"

### Предусловие:

#### Оплата по дебетовой карте:

1. Открываем браузер 
2. Заходим на страницу https://localhost:8080
3. Вводим номер карты 4444 4444 4444 4441 (статус карты:APPROVED)
4. Заполняем все поля карты
5. После заполнения всех данных на странице нажать кнопку "Купить"

#### Уникальная технология: выдача кредита по данным банковской карты:

1. Открываем браузер
2. Заходим на страницу https://localhost:8080
3. Вводим номер карты 4444 4444 4444 4442 (статус карты:DECLINED)
4. Заполняем все поля карты
5. После заполнения всех данных на странице нажать кнопку "Купить в кредит"





### Для тестирования предоставлено:

- Доступ к сайту банка через запуск SUT
- Доступ к эмулятору gate-simulator.
- База MySQL
- База PostresQL


### Примечание:

- Для тестирования страницы необходим подключить доступ к SUT.


## Перечень автоматизируемых тестовых сценариев:

### Позитивный сценарий для покупки тура с помощью дебетовой карты и карты для оформления кредита.



<table>
    <tr>
        <th>№</th>
        <th>Название</th>
        <th>Предисловие</th>
        <th>Шаги</th>
        <th>Ожидаемый результат</th>
    </tr>
    <tr>
        <td>1</td>
        <td> Оплата через поле «Купить» по карте "4444 4444 4444 4441"</td>
        <td> Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>Заполняем все поля валидным значением и нажимаем кнопку "Продолжить"</td>
        <td>В верхнем правом углу появляется сообщение "Успешно. Операция одобрена Банком". В Базе данных отображается запись APPROVED.</td>
    </tr>
    <tr>
        <td>2</td>
        <td> Оплата через поле «Купить в кредит» по карте "4444 4444 4444 4441"</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>Заполняем все поля невалидным данными и нажимаем кнопку "Продолжить"</td>
        <td>В верхнем правом углу появляется сообщение "Успешно. Операция одобрена Банком". В Базе данных отображается запись APPROVED</td>
    </tr>
    <tr>
        <td>3</td>
        <td>Оплата через поле «Купить» по карте "4444 4444 4444 4442" </td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td> Заполняем все поля невалидным значением "4444 4444 4444 4442" и нажимаем кнопку "Продолжить"</td>
        <td>В верхнем правом углу появляется сообщение "Ошибка! Банк отказал в проведении операции". В Базе данных отображается запись DECLINED.  </td>
    </tr>
    <tr>
        <td>4</td>
        <td>Оплата через поле «Купить в кредит» по карте "4444 4444 4444 4442"</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td> Заполняем все поля невалидным значением и нажимаем кнопку "Продолжить</td>
        <td>В верхнем правом углу появляется сообщение "Ошибка! Банк отказал в проведении операции". В Базе данных отображается запись DECLINED. </td>
    </tr>
 <tr>
</table>


### Негативный сценарий для покупки тура с помощью дебетовой карты и карты для оформления кредита.


#### Заполнение карты поле "Номер карты"
<table>
    <tr>
        <th>№</th>
        <th>Название</th>
        <th>Предисловие</th>
        <th>Шаги</th>
        <th>Ожидаемый результат</th>
    </tr>
        <tr>
        <td>1</td>
        <td>Неверно заполнено поле "Номер карты", пустое значение</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>Заполняем все поля кроме "Номер карты" валидными данными. Оставляем поле "Номер карты" пустым и нажимаем кнопку "Продолжить"</td>
        <td>Поле "Номер карты" отображено красным цветом, под полем надпись "Поле обязательно для заполнения"</td>
    </tr>
 <tr>
        <td>2</td>
        <td>Неверно заполнено поле "Номер карты", при вводе букв латиницей</td>
        <td>Зайти в браузер, https://localhost:8080, открывается форма оплаты</td>
        <td>В поле "Номер карты" вводим значение буквами "qwerty"</td>
        <td>Нет возможности введения букв, поле остаётся пустым</td>
    </tr>
    <tr>
        <td>3</td>
        <td>Неверно заполнено поле "Номер карты", при вводе спецсимволов</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>В поле "Номер карты" вводим значение "!#№%"</td>
        <td>Нет возможности введения символов, поле остаётся пустым</td>
    </tr>
 <tr>
        <td>4</td>
         <td>Неверно заполнено поле "Номер карты", 16 нулей</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>1. Вводим значение "0000 0000 0000 0000" в поле "Номер карты"  
          2. Остальные поля заполняем валидными данными и нажимаем кнопку"Продолжить" </td>
        <td>Поле заполнено значением "0000 0000 0000 0000,после нажатия кнопки "Продолжить", поле "Номер карты" отображено красным цветом, под полем надпись "Неверный формат" "</td>
    </tr>
 <tr>
        <td>5</td>
        <td>Неверно заполнено поле "Номер карты", слишком короткое значение, 15 цифр</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>1. В поле "Номер карты" вводим значение "4444 4444 4444 444 .
         2. Остальные поля заполняем валидными данными и нажимаем кнопку "Продолжить" </td>
        <td> Поле заполнено значением "4444 4444 4444 444..., после нажатия кнопки "Продолжить", поле "Номер карты" отображено красным цветом, под полем надпись "Неверный формат"</td>
 </tr>
</table>


#### Заполнение карты поле "Месяц"


<table>
<tr>
        <th>№</th>
        <th>Название</th>
        <th>Предисловие</th>
        <th>Шаги</th>
        <th>Ожидаемый результат</th>
    </tr>
 <tr>
        <td>1</td>
        <td>Неверно заполнено поле "Месяц", пустое значение</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>Заполняем все поля кроме "Месяц" валидными данными. Оставляем поле "Месяц" пустым и нажимаем кнопку "Продолжить"</td>
        <td>Поле "Месяц" отображено красным цветом, под полем надпись "Поле обязательно для заполнения"</td>
    </tr>
 <tr>
        <td>2</td>
        <td>Неверно заполнено поле "Месяц", при вводе букв латиницей</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>В поле "Месяц" вводим значение "dr"</td>
        <td>Символы не вводятся, поле остаётся пустым</td>
    </tr>
  <tr>
        <td>3</td>
        <td>Неверно заполнено поле "Месяц", при вводе спецсимволов</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>В поле "Месяц" вводим значение "!#"</td>
        <td>Символы не вводятся, поле остаётся пустым</td>
    </tr>
<tr>
        <td>4</td>
        <td>Неверно заполнено поле "Месяц", при вводе одной цифры</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>В поле "Месяц" вводим значение "5"</td>
        <td>Поле "Месяц" отображено красным цветом, под полем надпись "Поле обязательно для заполнения"</td>
    </tr>
 <tr>
        <td>5</td>
        <td>Неверно заполнено поле "Месяц", значения больше 12</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>В поле "Месяц" вводим значение "13</td>
        <td>Поле "Месяц" отображено красным цветом, под полем надпись "Неверный формат"</td>
    </tr>
 <tr>
        <td>6</td>
        <td>Неверно заполнено поле "Месяц", введены нули</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>В поле "Месяц" вводим значение "00"</td>
        <td>Поле заполнено значением "00"</td>
    </tr>
 <tr>
</table>


#### Заполнение карты поля "Год"
<table>
<tr>
        <th>№</th>
        <th>Название</th>
        <th>Предисловие</th>
        <th>Шаги</th>
        <th>Ожидаемый результат</th>
  </tr>
  <tr>
        <td>1</td>
        <td>Неверно заполнено поле "Год", пустое значение</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>Заполняем все поля кроме "Год" валидными данными. Оставить поле "Год" пустым и нажимаем кнопку "Продолжить </td>
        <td>Поле "Год" отображено красным цветом, под полем надпись "Неверный формат".</td>
    </tr>
 <tr>
        <td>2</td>
        <td>Неверно заполнено поле "Год", ввод букв</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>В поле "Год" вводим значение "rw"</td>
        <td>Символы не вводятся, поле остаётся пустым</td>
    </tr>
 <tr>
        <td>3</td>
        <td>Неверно заполнено поле "Год", ввод спецсимволов</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>В поле "Год" вводим значение "&%"</td>
        <td>Символы не вводятся, поле остаётся пустым</td>
    </tr>
 <tr>
        <td>4</td>
        <td>Неверно заполнено поле "Год", слишком короткое значение</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>Вводим "2" в поле "Год".2. Остальные поля заполняем валидными данными и нажимаем кнопку "Продолжить"</td>
        <td>Поле заполнено значением "2". Поле "Год" отображено красным цветом, под полем надпись "Неверный формат".</td>
    </tr>
  <tr>
<td>4</td>
        <td>Неверно заполнено поле "Год", введены нули </td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>В поле "Год" вводим значение "00". Остальные поля заполняем валидными данными и нажимаем кнопку "Продолжить"</td>
        <td>Поле заполнено значением "00". Поле "Год" отображено красным цветом, под полем надпись "Истёк срок действия карты".</td>
    </tr>
  <tr>
</table>

#### Заполнение карты поля "Владелец"

<table>
<tr>
        <th>№</th>
        <th>Название</th>
        <th>Предисловие</th>
        <th>Шаги</th>
        <th>Ожидаемый результат</th>
  </tr>
 <tr>
        <td>1</td>
        <td>Неверно заполнено поле "Владелец", пустое значение</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td> Заполняем все поля кроме "Владелец" валидными данными. Оставить поле "Владелец" пустым и нажимаем кнопку "Продолжить"</td>
        <td>Поле "Владелец" подсвечено красным цветом, под полем сообщение "Поле обязательно для заполнения"</td>
    </tr>
 <tr>
        <td>2</td>
        <td>Неверно заполнено поле "Владелец", ввод спецсимволов</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>В поле "Владелец" вводим значение "!#@№%"</td>
        <td>Символы не вводятся, поле остаётся пустым</td>
    </tr>
 <tr>
        <td>3</td>
        <td>Неверно заполнено поле "Владелец", ввод цифр</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>В поле "Владелец" вводим значение "123"</td>
        <td>Символы не вводятся, поле остаётся пустым</td>
    </tr>
  <tr>
         <td>4</td>
        <td>Неверно заполнено поле "Владелец", ввод кириллицы</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>В поле "Владелец" вводим значение "Иван"</td>
        <td>Символы не вводятся, поле остаётся пустым</td>
    </tr>
</table>

#### Заполнение карты поля "CVC/CVV"

<table>
<tr>
        <th>№</th>
        <th>Название</th>
        <th>Предисловие</th>
        <th>Шаги</th>
        <th>Ожидаемый результат</th>
  </tr>
 <tr>
        <td>1</td>
        <td>Неверно заполнено поле "CVC/CVV", пустое значение</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>Заполняем все поля кроме "CVC/CVV" валидными данными. Оставляем поле "CVC/CVV" пустым и нажимаем кнопку "Продолжить</td>
        <td>Поле "CVC/CVV" отображено красным цветом, под полем надпись "Неверный формат"</td>
    </tr>
 <tr>
        <td>2</td>
        <td>Неверно заполнено поле "CVC/CVV", ввод букв</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>В поле "CVC/CVV" вводим значение "ABC"</td>
        <td> Символы не вводятся, поле остаётся пустым</td>
    </tr>
 <tr>
        <td>3</td>
        <td>Неверно заполнено поле "CVC/CVV", ввод спецсимволов</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td> В поле "CVC/CVV" вводим значение "!#%"</td>
        <td>Символы не вводятся, поле остаётся пустым</td>
    </tr>
  <tr>
         <td>4</td>
        <td>Неверно заполнено поле "CVC/CVV", слишком короткое значение</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>Водим значение "12" в поле "CVC/CVV". Остальные поля заполняем валидными данными и нажимаем кнопку "Продолжить"</td>
        <td>Поле заполнено значением "12. Поле "CVC/CVV" отображено красным цветом, под полем надпись "Неверный формат"."</td>
    </tr>
 <tr>
        <td>5</td>
        <td>Неверно заполнено поле "CVC/CVV", нули</td>
        <td>Зайти в браузер, ввести https://localhost:8080, открывается форма оплаты</td>
        <td>1. В поле "CVC/CVV" вводим значение "000". 2. Остальные поля заполняем валидными данными и нажимаем кнопку "Продолжить"</td>
        <td>Поле заполнено значением "000". Поле "CVC/CVV" подсвечено красным цветом, под полем сообщение "Неверный формат" </td>
    </tr>
</table>



## Перечень используемых инструментов с обоснованием выбора.

- Docker - создание среды для тестирования
- Gradle - система сборки для Java. Помогает подготовить код к запуску, например скомпилировать и запустить тесты.
- Selenide - для удобства автоматизации тестов
- JUnit - для написания тестов
- Git - для отправки своего проекта на GitHub
- Postman - для проверки работы API
- DBeaver - для подключения к БД
- Lombok - библиотека для Java
- Allure - фреймворк, предназначенный для создания визуально наглядной картины выполнения тестов.



## Перечень и описание возможных рисков при автоматизации:

- Неверно рассчитано время выполнения работ;
- Отсутствие документации;
- Риск некорректной настройки запуска симулятора;
- Риск некорректной настройки БД;
- Риски производительности: автоматизированные тесты могут занимать много времени на выполнение;

## Интервальная оценка с учётом рисков в часах:

- Подготовка к тестированию, написание тестовых сценариев 12ч
- Выполнение тестов и их доработка 24ч
- Составление отчётов о тестировании 6ч

## План сдачи работ:

- Плановая готовность автотестов 1.08
- Плановая готовность результатов прогона автотестов 08.08
- Плановая готовность отчёта по автоматизации 14.08