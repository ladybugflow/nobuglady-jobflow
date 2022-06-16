## NobugLady-jobflow
![](https://img.shields.io/badge/license-Apache-green)
![](https://img.shields.io/badge/database-mysql-red)
![](https://img.shields.io/badge/build-gradle-yellow)
![](https://img.shields.io/badge/framework-springboot-blue)
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
