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
   
   #### Sequential execution
   
   <img src="https://github.com/nobuglady/nobuglady-jobflow/blob/main/readme/3.png?raw=true" alt="" width="400px"/>

   #### Parallel execution
   
   <img src="https://github.com/nobuglady/nobuglady-jobflow/blob/main/readme/4.png?raw=true" alt="" width="250px"/>

   #### Branch join
   
   <img src="https://github.com/nobuglady/nobuglady-jobflow/blob/main/readme/5.png?raw=true" alt="" width="350px"/>

2. We provide scheduled tasks, you flow can start automatically at scheduled times.

<img src="https://github.com/nobuglady/nobuglady-jobflow/blob/main/readme/6.png?raw=true" alt="" width="400px"/>

3. The processes of editing, published (service) and running (instance) are managed separately.

   Editing->(Publish)->Service->(Start)->Instance

4. You can restart the specified process/node in a history versions.

<img src="https://github.com/nobuglady/nobuglady-jobflow/blob/main/readme/7.png?raw=true" alt="" width="400px"/>

### :blue_book: Usage

#### 1. Create a flow

<img src="https://github.com/nobuglady/nobuglady-jobflow/blob/main/readme/8.png?raw=true" alt="" width="400px"/>

#### 2. Publish flow

<img src="https://github.com/nobuglady/nobuglady-jobflow/blob/main/readme/9.png?raw=true" alt="" width="400px"/>

#### 3. Run the flow

<img src="https://github.com/nobuglady/nobuglady-jobflow/blob/main/readme/10.png?raw=true" alt="" width="400px"/>

### :blue_book: Installation

#### 1. Install the database

   we use mysql database, you can download from [https://www.mysql.com/]

#### 2. Import initial data

   Checkout [script.sql](https://github.com/nobuglady/nobuglady-jobflow/blob/main/db/script.sql) and connect your mysql server, run below command.
   ```
   mysql> source script.sql;
   ```
   
#### 3. Download the code
  
   Dowload [last release](https://github.com/nobuglady/nobuglady-jobflow/tags) into you local.

#### 4. Build

   ```
   $ gradlew build -x test 
   ```
#### 5. Run

   ```
   $ java -jar build/libs/nobuglady-jobflow-0.0.1-SNAPSHOT.jar 
   ```
   The default login account is admin/admin
