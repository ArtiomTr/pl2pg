PL/SQL parser

## Building project

### Using plain maven setup

For initial setup, run following command:

```
mvn
```

Built program will appear in `bin` directory.

### Using Docker

Build docker image:

```
docker build . -f Dockerfile -t pl2pg --progress plain
```

It will automatically run tests.

## Tests

All tests are in `./src/test` directory. For more information, see [tests documentation](./src/test/README.md).
