# Game Library Service

A tool for game libraries

### Project description

The purpose is to create a tool for a french game-library. The project is designed around this particular client
use cases but it should be flexible as well as documented enough to adapt to your own use cases.
The project is composed by a REST service (this repo) and web client

### Service's technologies

The project was set up with [Spring initializr](https://start.spring.io/).
It will consume a PostgreSQL 9.6 database.
The user session, login / logout, security and more are delegated to a [Keycloak](https://www.keycloak.org/) service.
