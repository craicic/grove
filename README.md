# Grove, a tool for game libraries

A tool for game libraries, the goal is to manage a set of game the library owns and borrows. \
[Current version is 1.1.0](./CHANGELOG.md)

# How it's done ?

The tool is composed by three components :

- ##### Data : a postgresQL database.
    That runs on a native postgresQL server for production. The server is in a containers for development and tests.

- ##### Backend : a Spring REST service.
    Built with Maven, it relies on Hibernate/Spring data JPA repositories. Also using Testcontainers for IT. \
    [README is here !](./grove-service/README.md)
 
- ##### Frontend : an angular client app.
  You can find the [README here](./grove-webapp/README.md).


# Project Guidelines

- ### Project language

Documentation, Github issues and discutions, Javadoc, and comments in code should be written in english.
An exception is made for all client-wise documents.

- ### Naming

Naming should follow Java / Typescript practices. Name of variables, classes, files, folders etc... should be written in
english.

- ### Git Workflow

Versioning practices should be based on the following Vincent Driessen's
article : [https://nvie.com/posts/a-successful-git-branching-model/](https://nvie.com/posts/a-successful-git-branching-model/)

Release branches are optional : tags in main branch may be enough.

# Deploy the application
You'll find a [guide's here](./grove-service/README.md) to deploy the backend. \
The angular app should be built using `ng build`. More info [here](./grove-webapp/README.md).
 

# Prepare your environment to start coding

Before you start, define a username `POSTGRES_USR` and a password `POSTGRES_PWD` for the postgres database. Do the same
for pgadmin `PG_EMAIL` and `PG_PWD`.

## Docker containers setup

There is two containers for this project : the first is a postgres server, the second one is a pgadmin server.

##### 1 : Install Compose (or docker desktop)

Here's the guide : [docs.docker.com/compose/install/](https://docs.docker.com/compose/install/)

##### 2 : Create the pgadmin server file

Create a new file `docker/pgadmin/servers.json` that contains :

```    json
    {
      "Servers": {
        "1": {
          "Name": "localhost",
          "Group": "Servers",
          "Port": 5432,
          "Username": "[insert the postgres username you defined]",
          "Password": "[insert the postgres password you defined]",
          "Host": "game-library-dev-db",
          "SSLMode": "prefer",
          "MaintenanceDB": "postgres"
        }
      }
    }
```
it avoids to expose your database credential. That's why this file is a git-ignored.

##### 3 : Environment

Add the following environment variable for docker with the values you defined before you start :
`POSTGRES_USR`, `POSTGRES_PWD`, `PG_EMAIL` and `PG_PWD`.

##### 4 : run the docker compose

Execute a `docker-compose up` and check that all went green.

## Spring REST service setup

Download and [install JDK](https://adoptium.net/temurin/releases/?version=19) version 19.

Download and [install Maven](https://maven.apache.org/install.html).

Create a new file `game-library/src/test/resources/secrets.properties` \
It should contain following line, with the correct credentials.

```properties
spring.datasource.username=[insert the postgres username you defined]
spring.datasource.password=[insert the postgres password you defined]
```
This file is referenced in the `application.properties` and it avoids to expose your database credential. That's why
this file is a git-ignored.

Run the application.

## Angular client app setup

Run a `ng serve` with the node option `--openssl-legacy-provider`.

### *Happy coding !*

