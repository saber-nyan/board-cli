[![Star this repo](http://githubbadges.com/star.svg?user=saber-nyan&repo=board-cli&style=flat)](https://github.com/saber-nyan/board-cli)
[![Fork this repo](http://githubbadges.com/fork.svg?user=saber-nyan&repo=board-cli&style=flat)](https://github.com/saber-nyan/board-cli/fork)
[![Issues](https://img.shields.io/github/issues/saber-nyan/board-cli.svg)](https://github.com/saber-nyan/board-cli/issues)
[![Release](http://github-release-version.herokuapp.com/github/saber-nyan/board-cli/release.svg?style=flat)](https://github.com/saber-nyan/board-cli/releases/latest)
# board-cli
Консольный клиент для имиджборд.
Работоспособность на M$ Windows возможна, **но не гарантируется**.

Согласен, только наркоманы пишут CLI-проекты на Java.

*Попытайся его использовать. Готов только модуль харкача, работа с сетью (но не просмотр и постинг!)*

![Screenshot](http://i.imgur.com/0bEB430.png)
## Что нужно?
* **Java**>=1.8, подойдет как и Oracle, так и OpenJDK (icedtea).
* **IntelliJ IDEA** (нужна только для сборки) *Простите.*

## Как запустить?
1. Качаешь `board-core.jar` и `board-module-name.jar` из [Releases](https://github.com/saber-nyan/board-cli/releases/latest).
2. Кладешь их рядом.
3. Запускаешь `java -jar ./board-core.jar`.
4. ??????
5. *Так, а как постить-то?*

## Как собрать?
1. Качаешь [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) (Community-версию, она под Apache 2.0).
2. Устанавливаешь по инструкции из архива.
3. Открываешь `board-cli.iml` из корня проекта.
4. Вызываешь `Build → Build Artifacts... → All Artifacts → Build`.
5. Находишь jar-файлы в `/board-cli/out/artifacts/jar/`.
6. Запускаешь!

### Насчет API...
Будут достаточно часто меняться. Для начала могу переименовать методы (или нет), добавлю методы для постинга.

### ...и документации.
Будет, но не сразу. Олсо, пока все достаточно очевидно

## Так почему IntelliJ IDEA?
Не то чтобы я не осилил системы сборки вроде Gradle/Maven/etc, но в IDE они настолько криво работали (особенно с зависимостями), что **гораздо** проще было сделать так. Но однажды я соберусь и решу таки все проблемы с Gradle.

## Что использовалось?
* [org.json:json (20170516)](https://github.com/stleary/JSON-java) <JSON> Парсинг JSON. В OpenJDK он **внезапно**, не встроен.
* [com.googlecode.lanterna:lanterna (3.0.0-rc1)](https://github.com/mabe02/lanterna) <LGPL v3.0> Гуец. Вполне простой, но пока хз, что получится.

## Лицензия?
[![GPL Licence](https://badges.frapsoft.com/os/gpl/gpl.svg?v=103)](https://opensource.org/licenses/GPL-3.0/)

Потому что могу.
