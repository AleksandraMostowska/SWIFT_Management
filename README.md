# SWIFT Management

## Description
SWIFT Management is a Java-based application designed to manage SWIFT codes for headquarters and branches. 
The application interacts with a MySQL database and exposes RESTful endpoints for data retrieval and manipulation.

## Prerequisites
Before running the application, make sure the following tools are installed:
- **Docker** (for running the application and MySQL container)
- **Docker Compose** (for orchestrating the containers)
- **Java 11 or higher** (if you plan to run the application outside Docker)

## Setup

### Step 1: Clone the repository

Clone the project repository to your local machine:

<p>
  git clone https://github.com/AleksandraMostowska/SWIFT_Management.git<br>
  cd SWIFT_Management
</p>



### Step 2: Build and run the project using Docker Compose
To build and run the project with Docker and Docker Compose, use the following command:

docker-compose up -d --build


### Step 3: Access the application
Once the containers are running, you can access the application by navigating to:

http://localhost:8080


### Step 4: Access the MySQL Database
If you want to access the MySQL database directly, you can connect to the MySQL container using the following command:

docker exec -it swift_management-mysql-1 mysql -u user -p db_1

<p>
  Enter the password (user1234) when prompted.<br>
  The name of the docker container may be found under command:<br>
  docker ps 
</p>

### Application Environment Configuration

The application uses an application.properties file for database configuration. Below are the key properties:

<p>
  db.url=jdbc:mysql://mysql:3307/db_1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC<br>
  db.username=user<br>
  db.password=user1234
</p>



### Interacting with the API
The web application exposes several RESTful endpoints. Here are some example endpoints you can use to interact with the data:

<p>
  GET /v1/swift-codes/{swift_code} : Get all headquarters with branches or branches data.<br>
  GET /v1/swift-codes/country/{countryISO2} : Get all headquarters with branches or branches data for specified country by country ISO2 code.<br>
  POST /v1/swift-codes: Posts new data.<br>
  DELETE /v1/swift-codes/{swift_code}: Deletes chosen data by swift code.<br>
</p>

### Postman Documentation
You can access the full API documentation using the following Postman link:

[Postman API Documentation](https://documenter.getpostman.com/view/36744686/2sAYXEFJpi)

### Database Initialization
The MySQL container will automatically load a dump of the database when it's started for the first time. 
The dump.sql file is located in the mysql_data folder, and it will populate the database with initial data.

If you want to add or modify the data in the database, you can:
Connect to the MySQL database directly from the MySQL container.
Run SQL queries to modify the data as needed.


### Running Tests
If you want to run unit tests for the project, you can do so by executing the following command in your terminal:

mvn clean test

This will run all unit tests and provide you with the test results.


### Troubleshooting

Problem 1: Docker Containers Not Starting
Solution: Ensure that Docker is running on your system. Run docker info to check the status of Docker.

Problem 2: Database Connection Issues
Solution: Make sure that the MySQL container is running and accessible via port 3307. Also, verify that the correct username and password (user and user1234) are being used.

