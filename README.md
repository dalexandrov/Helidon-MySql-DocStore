# Helidon Demo with MySQL Document Store

A demo for using Helidon to access a MySQL Document Store

Helidon variation of https://github.com/boyzoid/micronaut-document-store-demo


## Setup

This setup assumes you already have access to a MySQL database.
*
* Open MySQL Shell and connect to your MySQL instance using the following command: `\c {user}:{password}@{host}:33060`

    * Where `{user}` is the username, `{password}` is the password, and `{host}` is the server domain name or IP address of your MySQL instance.
* In MySQL Shell, run the command `session.createSchema('mn_demo')` to create the new schema.
* In MySQL Shell, run the following command: `util.importJson( '/absolute/path/to/project/data/scores.json', {schema: 'mn_demo', collection: 'pokemon'})`

    * If the process runs successfully, you will see output similar to this:
      `.. 6.. 6
      Processed 218 bytes in 6 documents in 0.0076 sec (6.00 documents/s)`

## Run

Build:

```bash
mvn clean package
```

Run: 

```bash
java -jar target/doc.jar
```

Test:

```bash
curl -X GET http://localhost:8080/pokemons
```

or with limit:

```bash
curl -X GET http://localhost:8080/pokemons/3
```

Try to add a Pokemon:

```bash
curl -X POST -H "Content-Type: application/json" -d '{"name" : "Poki", "type" : "3"}' http://localhost:8080/pokemons
1
```

See if the new Pokemon is present:

or with limit:

```bash
curl -X GET http://localhost:8080/pokemons/10
```

Try to update:

```bash
 curl -X PUT http://localhost:8080/pokemons/{id}/Pok
 1
```

Delete a pokemon:

```
curl -X DELETE http://localhost:8080/pokemons/{id}                
```

Test with get all Pokemons.
