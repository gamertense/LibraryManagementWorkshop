# Library Management Workshop

This project, named `LibraryManagementWorkshop`, is a hands-on workshop designed to demonstrate the development of a simple library management system using Copilot.

The system allows users to manage a collection of books, including adding new books, updating existing books, deleting books, and searching for books. It also handles user management, allowing users to check out and return books.

The project uses a MySQL database to store data, and the database can be easily started using a `run.sh` script. The application properties such as the application name, database URL, username, and password are configurable through the `application.properties` file.

This workshop is designed to be interactive and practical, providing developers with real-world experience in developing Spring Boot applications. It is suitable for developers who have a basic understanding of Java.
## Prerequisites

Make sure you have installed all of the following prerequisites on your development machine:

- Java JDK 17 or higher
- Gradle
- Docker

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

1. Clone the repo: `git clone git@github.com:palo-it-th/LibraryManagementWorkshop.git`
2. Navigate into the directory: `cd LibraryManagementWorkshop`

## Starting the Database

To start the database, you need to run the `run.sh` script. You can do this by running the following command in your terminal:

```bash
 sh run.sh database_start
```

To stop and clear the database, you can run the following command:

```bash
 sh run.sh database_clear
```
 
## Running the Application

After starting the database, you can run the application using Gradle:

```bash
./gradlew bootRun
```

This will start the Spring Boot application on your local machine.


## Instructions

For detailed instructions on how to use this application, please refer to the [Instruction.md](Instruction.md).

## License

This project is licensed under the MIT License - see the `LICENSE.md` file for details