Feature: HandShake

  Scenario: Проверка связи с сервером
    Given Дано парочку таких персон
      | name      | surname      | email      |
      | somename  | somesurname  | someemail  |
      | somename2 | somesurname2 | someemail2 |
    When клиент запросил проверку связи
    Then ответить что на сервере есть 2 клиента

  Scenario: Проверка связи с сервером когда клиентов нет
    Given Дано парочку таких персон
      | name      | surname      | email      |
    When клиент запросил проверку связи
    Then ответить что на сервере есть 0 клиента