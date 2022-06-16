## NobugLady-jobflow
![](https://img.shields.io/badge/license-Apache2.0-yellow)
![](https://img.shields.io/badge/database-Mysql10.4.13-red)
![](https://img.shields.io/badge/build-Gradle7.4.1-green)
![](https://img.shields.io/badge/framework-SpringBoot2.6.2-blue)
### Build
```
$ gradlew build -x test 
```
### Setup database
Checkout [script.sql](https://github.com/nobuglady/nobuglady-jobflow-db/blob/main/script.sql) and connect your mysql server, run below command.
```
mysql> source script.sql;
```
### Startup
```
$ java -jar build/libs/nobuglady-jobflow-0.0.1-SNAPSHOT.jar 
```
### Overview
![](https://github.com/nobuglady/nobuglady-jobflow/blob/main/readme/2.gif?raw=true)
