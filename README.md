# :izakaya_lantern:nobuglady-jobflow:izakaya_lantern:

![](https://img.shields.io/badge/license-Apache2.0-yellow)
![](https://img.shields.io/badge/database-Mysql10.4.13-red)
![](https://img.shields.io/badge/build-Gradle7.4.1-green)
![](https://img.shields.io/badge/framework-SpringBoot2.6.2-blue)

### :blue_book: Overview

![](https://github.com/nobuglady/nobuglady-jobflow/blob/main/readme/2.gif?raw=true)

### :blue_book: Features

1. For shell execution and http-based api calls, we provide a job-flow framework for calling according to the specified process.

   For example:
   sequential execution

   parallel execution

   branch join

2. We provide scheduled tasks, you flow can start automatically at scheduled times.

3. The processes of editing, published (service) and running (instance) are managed separately.

   Editing->(Publish)->Service->(Start)->Instance

4. You can restart the specified process/node in a history versions.

### :blue_book: Usage

1. Create a process

2. Release process

3. Run the process

### :blue_book: Installation

1. Install the database

   we use mysql database, you can download from [https://www.mysql.com/]

2. Import initial data

   Checkout [script.sql](https://github.com/nobuglady/nobuglady-jobflow-db/blob/main/script.sql) and connect your mysql server, run below command.
   ```
   mysql> source script.sql;
   ```
   
3. Download the code

4. Build

   ```
   $ gradlew build -x test 
   ```
5. Run

   ```
   $ java -jar build/libs/nobuglady-jobflow-0.0.1-SNAPSHOT.jar 
   ```
   the default login account is admin/admin
