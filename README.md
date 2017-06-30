# testTask
<h1><b>Test task by CrestWave</b></h1>

<h3><b>Не выполнено: </b></h3>
<p>
<ol>
<li>Не могу получить ответ от сокет-сервера. Так и не смог понять почему.</li>
<li>Не успел добавить реакцию в браузере</li>
<li>Не выводится инфа в HEX</li>
</ol>
</p>

Тестовое задание Java Junior Developer

Введение

Для знакомства с навыками прикладного программирования на языке Java соискателям работы на должность Java Junior Developer в компании CrestWave Tech предлагается выполнить тестовое задание. Для выполнения задания необходимо
познакомиться со следующими технологиями:
HTTP protocol
TCP sockets
Basic HTML forms
JAXB
HTTP Servlets
Jetty Servlet Container
Log4J
Jackson lib

Задание выполняется соискателем в любой из доступных сред разработки.

Верхнеуровневое описание модуля

Разрабатываемый модуль выполняет функцию конвертера протоколов. Для взаимодействия с пользователем модуль должен
предоставлять интерфейс в виде HTML страницы для отправки входного XML пакета. При получении данных формы на
стороне HTTP контроллера, модуль производить парсинг входного XML пакета и его валидацию. В случае успешного разбора пакета, производит конвертирование в формат JSON и записывает в лог. Пакет JSON отправляется в новом tcp коннекте на
адрес и порт из конфига. Пользователю выводится результат работы парсера и конвертера на веб странице и результат отправки JSON over TCP.

UI HTML

При заходе на веб сервер Jetty, который будет хостить приложение, по пути / пользователю должна отображаться веб форма. Форма должна содержать textarea name=”xml” и кнопку submit. Отправка данных методом POST. Страница результата выполнения разбора и конвертации XML отображается после сабмита формы. Дополнительным плюсом будет использование ajax сабмита формы с отправкой данных без перегрузки страницы.

HTTP контроллер, Jetty сервер

Выполняющий тестовое задание выбирает префикс пекиджа в котором будет работать. Запуск ява приложения должен осуществляться из класса $package.Bootstrap. В классе Bootstrap должно происходить считывание property файла с настройками из файловой системы из текущей директории процесса. Имя файла config.property. Файл должен содержать следующие настройки:

http.port=8081
tcp.dest.addr=127.0.0.1
tcp.dest.port=1234

После считывания файла настроек необходимо выполнить запуск embedded Jetty cервера на порту из перменной http.port. В
контексте jetty сервера необходимо зарегистрировать сервлет $package.http.XmlParserServlet.

Сервлет должен содержать код для обработки HTTP POST запроса, приходящего из HTML формы. Использование фасада или сервиса с вынесенным кодом конвертации и сетевого взаимодействия остается на усмотрение выполняющего задание.
XML пакет необходимо разобрать в объект $package.jaxb.Envelope используя технологию JAXB.

Пример XML пакета:

```xml
<Envelope xmlns:urn="wsapi:Payment" xmlns:uts="wsapi:Utils">
<Body>
<urn:sendPayment>
<token>001234</token>
<cardNumber>811626834823422</cardNumber>
<requestId>2255086658</requestId>
<amount>100000.00</amount>
<currency>RUB</currency>
<uts:account type="source">009037269229</uts:account>
<uts:account type="destination">088127269229</uts:account>
<page>1</page>
<field id="0" value="0800" />
<field id="11" value="000001" />
<field id="70" value="301" />
</urn:sendPayment>
</Body>
</Envelope>
```

В случае ошибки при разборе XML пакета, вывести информацию предоставленную парсером на страницу ошибки и показать пользователю. Записать информацию в лог.

В случае успешного разбора XML пакета выполнить конвертацию объекта в JSON используя библиотеку Jackson и записать результат конвертации в лог. Пользователю сообщить об успешной конвертации.

Открыть tcp соединение на адрес tcp.dest.addr и порт tcp.dest.port. Отправить в соединение данные:
Item Description Format and Value
Header fixed magic FFBBCCDD
Length variable json length 4 bytes little endian integer
Json byte[] of json string utf16-le charset

Предварительно записать отправляемые бинарные данные в лог в виде HEX.
Пользователю сообщить о результате отправки данных в сокет. В случае сетевой ошибки, отобразить ошибку.

Требования к сдаче тестового задания

Использование git во время разработки Желательно выложить результаты на github Использовать следующие системы сборки и управления зависимостями (в приоритете от бо