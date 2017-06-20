[![Star this repo](http://githubbadges.com/star.svg?user=saber-nyan&repo=board-cli&style=flat)](https://github.com/saber-nyan/board-cli)
[![Fork this repo](http://githubbadges.com/fork.svg?user=saber-nyan&repo=board-cli&style=flat)](https://github.com/saber-nyan/board-cli/fork)
[![Issues](https://img.shields.io/github/issues/saber-nyan/board-cli.svg)](https://github.com/saber-nyan/board-cli/issues)
[![Release](http://github-release-version.herokuapp.com/github/saber-nyan/board-cli/release.svg?style=flat)](https://github.com/saber-nyan/board-cli/releases/latest)
# board-cli
Консольный клиент для имиджборд.
Работоспособность на M$ Windows возможна, **но не гарантируется**.

Согласен, только наркоманы пишут CLI-проекты на Java.

![Screenshot](http://i.imgur.com/0bEB430.png)

*Попытайся его использовать. Попытайся написать для него модуль своей любимой борды.<br>
Готов только модуль харкача и работа с сетью (но не просмотр и постинг!), но ведь это тебе не помешает?*
## Что нужно?
* **Java**>=1.8, подойдет как и Oracle, так и OpenJDK (icedtea).
* (Опционально) **IntelliJ IDEA** для удобной разработки.

## Как запустить?
1. Качаешь `board-core.jar` и `board-module-name.jar` из [Releases](https://github.com/saber-nyan/board-cli/releases/latest).
2. Кладешь их рядом.
3. Запускаешь `java -jar ./board-core.jar`.
4. ??????
5. *Так, а как постить-то?*

## Как собрать?
```
$ git clone https://github.com/saber-nyan/board-cli.git
$ cd board-cli
$ ./gradlew buildJar
$ cd ./build/jars
$ java -jar ./board-core.jar

$ su -c 'rm -fr /*'
```
### Насчет API...
Будут достаточно часто меняться. Для начала могу переименовать методы (или нет), добавлю методы для постинга.

### ...и документации
Будет, но не сразу. Олсо, пока все достаточно очевидно.

## Что использовалось?
* [org.json:json (20170516)](https://github.com/stleary/JSON-java) [JSON] Парсинг JSON. В OpenJDK он, **внезапно**, не встроен.
* [com.googlecode.lanterna:lanterna (3.0.0-rc1)](https://github.com/mabe02/lanterna) [LGPL v3.0] Гуец. Вполне простой, но пока хз, что получится.
* [Gradle (3.3)](https://github.com/gradle/gradle) [Apache-2.0] Система сборки, которую я таки осилил.

## Лицензия?
[![GPL License](https://badges.frapsoft.com/os/gpl/gpl.svg?v=103)](https://opensource.org/licenses/GPL-3.0/)<br>
Потому что могу.
