# Grove. A Game Library Service

A tool for game libraries, the goal is to manage a set of game the library owns and borrows.

# Project Guidelines

- ### Project language

Documentation, Github issues and discutions, Javadoc, and comments in code should be written in english.
An exception is made for all client-wise documents.

- ### Naming

Naming should follow Java / Typescript practices. Name of variables, classes, files, folders etc... should be written in
english.

- ### Git Workflow

Versioning practices should be based on Vincent Driessen's
article : [A successful Git branching model](https://nvie.com/posts/a-successful-git-branching-model/)

# Prepare your environnement to start coding

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

##### 3 : Environment

Add the following environment variable for docker with the values you defined before you start :
`POSTGRES_USR`, `POSTGRES_PWD`, `PG_EMAIL` and `PG_PWD`.

##### 4 : run the docker compose

Execute a `docker-compose up` and check that all went green.

## Spring REST application setup

Download and [install JDK](https://adoptium.net/temurin/releases/?version=19) version 19.

Download and [install Maven](https://maven.apache.org/install.html).

Run the application.

## Angular client app setup

Run a `ng serve` command with the node argument `--openssl-legacy-provider`
