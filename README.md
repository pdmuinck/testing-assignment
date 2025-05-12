# testing-assignment

This tool returns all today's soccer games in csv format.

## Local setup

use asdf:
```shell
brew install asdf
```

Add asdf plugins:
```shell
asdf plugin add java
asdf plugin add maven
```

install java and maven:
```shell
asdf plugin install java
asdf plugin install maven
```

This should install the versions specified in the `.tool-versions` file.

## Build project
```shell
mvn clean package
```
## Run tests
```shell
mvn test
```

## Run
```shell
java -jar target/sportradar-0.1.0.jar-with-dependencies.jar
```
