PL/SQL parser

## Building project

### Using Intellij IDEA

It's recommended to open this project with [Intellij IDEA Community Edition](https://www.jetbrains.com/idea/). IDE will
automatically configure this project.

### Using plain maven setup

For initial setup, run following command:

```
mvn clean install
```

Run project tests with command:

```
mvn test
```

### Using Docker

Build docker image:

```
docker build . -f Dockerfile -t pl2pg --progress plain
```

It will automatically run tests.

## Tests

All tests are in `./src/test` directory. For more information, see [tests documentation](./src/test/README.md).

## Tasks

