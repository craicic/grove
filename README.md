# Game Library Service [![Build Status](https://travis-ci.org/xxjokerx/game-library-service.svg?branch=master)](https://travis-ci.org/xxjokerx/game-library-service)

A tool for game libraries

## Project description

The purpose is to create a tool for a french game-library. The project is being design around this particular client
use cases.
The project is composed by a web service (this repo) and an [Angular web client](https://github.com/xxjokerx/game-library-webapp).

## Technologies

The project was set up with [Spring initializr](https://start.spring.io/).
It will consume a PostgreSQL 9.6 database.
The user session, login / logout, security and more are delegated to a [Keycloak](https://www.keycloak.org/) service.



## Deployment

##### 1 - Prepare Java Development Kit, PostgreSQL and Maven
Download and [install JDK](https://adoptopenjdk.net/) version 11. 

Install [postgreSQL9](https://www.postgresql.org/download/) and pgAdmin 4, remember the user/password you set during the installation\
Run pgAdmin 4, go to Server/PostgreSQL right click Login/Group Roles and Create a new one.
Fill the username in general, in Definition enter a password. In Privileges enable 'can login?' and 'create database'.

Download and [install Maven](https://maven.apache.org/install.html).

##### 2 - Deploy keycloak server
Follow this guide : [Deploy and configure Keycloak](./KEYCLAOK.md)

##### 3 - Create the database
Run SQL Shell (psql), press enter 3 times then fill username and password. Then type : `CREATE DATABASE YOUR-DB-NAME WITH OWNER = YOUR-USERNAME;`.  
You can either do this step via pgAdmin 4.

##### 4 - Import the project
Download or clone this repository.

##### 5 - Rename application.properties.placeholder
Full path is `src/main/resources/application.properties.placeholder`.  
Rename it into `application.properties`.

##### 6 - Configure application.properties
Edit this file, you have 4 fields to complete.  

In section DATA SOURCE : `spring.datasource.url`, `spring.datasource.username` & `spring.datasource.password`.  
In section SECURITY / KEYCLOAK : `keycloak.credentials.secret`.

##### 7 - Compile and run the application

With a prompt in project root folder, run : `mvn package`. It will create a Target folder that contains your JAR.  
Then run : `java -jar \target\game-library-0.3.0-ALPHA.jar`. The version may vary.

*And it's done : the program will be launched through the embedded tomcat server using port 8080.*

## Current version

#### 1.0.0

This version is the first release.

## Upcoming version

#### 1.1.0-BETA

More realistic loan rules. Update most dependencies version.

## Changelog

#### 0.0.1-SNAPSHOT - 1 May 2020

Project structure is done.

#### 0.2.0-ALPHA - 20 May 2020

Repository fundamentals set, database is fill with demo data on startup.

#### 0.3.0-ALPHA

Theme CRUD feature, exposed on the service's API.

#### 0.4.0-ALPHA

Game related CRUD feature, exposed on the service's API.

#### 0.5.0-ALPHA

User / member features are done.

#### 0.6.0-ALPHA

Loans features are done.

#### 1.0.0

This version is the first release, not fully complete yet