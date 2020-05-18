# Game Library Service [![Build Status](https://travis-ci.org/xxjokerx/game-library-service.svg?branch=master)](https://travis-ci.org/xxjokerx/game-library-service)

A tool for game libraries

### Project description

The purpose is to create a tool for a french game-library. The project is designed around this particular client
use cases but it should be flexible as well as documented enough to adapt to your own use cases.
The project is composed by a REST service (this repo) and web client.

### Service's technologies

The project was set up with [Spring initializr](https://start.spring.io/).
It will consume a PostgreSQL 9.6 database.
The user session, login / logout, security and more are delegated to a [Keycloak](https://www.keycloak.org/) service.

## Current version
#### 0.1.0-ALPHA
Application's model with hibernate and constrains.

## Upcoming version
#### 0.2.0-ALPHA
Repository fundamentals sets, fill database on startup

## Changelog
#### 0.0.1-SNAPSHOT - 1 May 2020
Project structure is done.