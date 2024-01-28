[![Todo CI](https://github.com/panuphanch/todo-api/actions/workflows/scala.yml/badge.svg)](https://github.com/panuphanch/todo-api/actions/workflows/scala.yml)

# Todo-API with Scala, Finatra-HTTP, and Guice

## Overview

This repository contains a simple todo API implemented in Scala using the Finatra-HTTP framework for handling HTTP requests and responses. Dependency injection is managed through Guice. The API provides functionality to manage todo lists, including creating new todos and organizing them into different lists.

## Features

- **HTTP Endpoints**: The API exposes HTTP endpoints for managing todo lists.
- **Dependency Injection**: Guice is used for dependency injection to facilitate modularity.
- **Testability**: Provides test cases using Finatra's FeatureTest and mock objects for HTTP requests.

## Getting Started

Follow these steps to get the todo API up and running on your local machine.

### Prerequisites

- [Java](https://www.oracle.com/java/technologies/downloads/#java17) (version 17)
- [Scala](https://www.scala-lang.org/download/) (Scala Build Tool)

### Installation

Clone the repository:

```bash
git clone https://github.com/panuphanch/todo-api.git
cd todo-api
```

Build the project:

```bash
sbt compile
```

### Usage

Run the embedded HTTP server

The API will be accessible at `http://localhost:8888`

```bash
sbt run
```

Test the API using your preferred HTTP clien or tools like `curl`.

Try to call `ping` should return `pong`
```bash
curl http://localhost:8888/ping
```

## Testing

To run the test suite, use the following command:

```bash
sbt test
```

## Acknowledgments
- [Finatra-HTTP](https://github.com/twitter/finatra) - The framework used for handling HTTP requests.
- [Guice](https://github.com/google/guice) - Used for dependency injection.
- Other dependencies or tools used in the project.
