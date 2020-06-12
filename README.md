# Game Library Service [![Build Status](https://travis-ci.org/xxjokerx/game-library-service.svg?branch=master)](https://travis-ci.org/xxjokerx/game-library-service)

A tool for game libraries

### Project description

The purpose is to create a tool for a french game-library. The project is designed around this particular client
use cases.
The project is composed by a web service (this repo) and web client.

### Service's technologies

The project was set up with [Spring initializr](https://start.spring.io/).
It will consume a PostgreSQL 9.6 database.
The user session, login / logout, security and more are delegated to a [Keycloak](https://www.keycloak.org/) service.

## Current version
#### 0.3.0-ALPHA
Theme CRUD feature, exposed on the service's API

## Upcoming version
#### 0.4.0-ALPHA
Game related CRUD feature, exposed on the service's API

## Changelog
#### 0.0.1-SNAPSHOT - 1 May 2020
Project structure is done.

#### 0.2.0-ALPHA - 20 May 2020
Repository fundamentals set, database is fill with demo data on startup

#### 0.3.0-ALPHA
Theme CRUD feature, exposed on the service's API