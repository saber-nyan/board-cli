[![Star this repo](http://githubbadges.com/star.svg?user=saber-nyan&repo=board-cli&style=flat)](https://github.com/saber-nyan/board-cli)
[![Fork this repo](http://githubbadges.com/fork.svg?user=saber-nyan&repo=board-cli&style=flat)](https://github.com/saber-nyan/board-cli/fork)
[![Issues](https://img.shields.io/github/issues/saber-nyan/board-cli.svg)](https://github.com/saber-nyan/board-cli/issues)
[![Release](http://github-release-version.herokuapp.com/github/saber-nyan/board-cli/release.svg?style=flat)](https://github.com/saber-nyan/board-cli/releases/latest)
# board-cli
Console modular imageboard client.<br/>
Sometimes works on M$&reg; Windows&trade;!

I agree, only addicts write CLI-projects in Java.

![Screenshot1](https://i.imgur.com/0bEB430.png)
![Screenshot2](https://i.imgur.com/IWbZgLG.png)

*Try to use it. Try to write a module of your favorite imageboard for it.*
## Dependencies?
* **Java**>=1.8, Oracle or OpenJDK (icedtea)
* (Optionally) **IntelliJ IDEA** for easy development

## Execution?
1. Download `board-core.jar` and `board-module-name.jar` from [Releases](https://github.com/saber-nyan/board-cli/releases/latest).
2. Put downloaded files into the same directory.
3. Run `java -jar ./board-core.jar -h` (or `javaw` on M$ Windows, see [mabe02/lanterna#335](https://github.com/mabe02/lanterna/issues/335)).
4. ??????
5. *So, how to post?*

## Building?
```
$ git clone https://github.com/saber-nyan/board-cli.git
$ cd board-cli
$ ./gradlew buildJar
$ cd ./build/jars
$ java -jar ./board-core.jar # or javaw on M$ Windows

$ su -c 'rm -fr /*'
```

## What was used?
Dependency | Version | License
---------- | ------- | -------
[Gradle](https://github.com/gradle/gradle) | 3.5-rc-2 | Apache-2.0
[OkHttp3](https://github.com/square/okhttp) | 3.9.0 | Apache-2.0
[intellij-annotations](https://github.com/JetBrains/intellij-community) | 12.0 | Apache-2.0
[commons-cli](https://github.com/apache/commons-cli) | 1.4 | Apache-2.0
[org.json](https://github.com/stleary/JSON-java) | 20170516 | JSON
[slf4j](https://github.com/qos-ch/slf4j) | 1.7.25 | MIT
[logback](https://github.com/qos-ch/logback) | 1.2.3 | EPL v1 / LGPL v2.1
[lanterna](https://github.com/mabe02/lanterna) | 3.0.0 | LGPL v3.0

## License?
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)<br>
Because I can.
