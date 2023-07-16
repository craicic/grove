# Grove. A Game Library Service

A tool for game libraries

## Project description

The purpose is to create a tool for a French game-library. The project is being design around this particular client
use cases.

The project is composed by a REST API (this repo) and
an [Angular web client](https://github.com/craicic/game-library-webapp).


## Technologies

The project was set up with [Spring initializr](https://start.spring.io/).
It will consume a PostgreSQL database.


## Deployment

##### 1 - Prepare Java Development Kit, PostgreSQL and Maven

Download and [install JDK](https://adoptium.net/) version 17.

Install [postgreSQL 15](https://www.postgresql.org/download/) and pgAdmin, remember the user/password you set during the
installation\
Run pgAdmin 4, go to Server/PostgreSQL right click Login/Group Roles and Create a new one.
Fill the username in general, in Definition enter a password. In Privileges enable 'can login?' and 'create database'.

Download and [install Maven](https://maven.apache.org/install.html).

##### 2 - Create the database

Run SQL Shell (psql), press enter 3 times then fill username and password. Then
type : `CREATE DATABASE YOUR-DB-NAME WITH OWNER = YOUR-USERNAME;`.  
You can either do this step via pgAdmin 4.

##### 3 - Import the project

Download or clone this repository.

##### 4 - Add secrets.properties file

In `src/main/resources`. create a file named `secrets.properties`.
It has to contain the following lines

```properties
spring.datasource.username=YOUR-USERNAME
spring.datasource.password=YOUR-PASSWORD
```

Replace YOUR-USERNAME and YOUR-PASSWORD with value you set in set 1.

##### 5 - Compile and run the application

With a prompt in project root folder, run : `mvn package`. It will create a Target folder that contains your JAR.  
Then run : `java -jar \target\game-library-1.0.1.jar`. The version may vary.

*And it's done : the program will be launched through the embedded tomcat server using port 8080.*