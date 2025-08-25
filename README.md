# OAuth2SecureService 🚀

![OAuth2SecureService](https://img.shields.io/badge/OAuth2SecureService-v1.0.0-blue.svg)  
[![Release](https://img.shields.io/badge/Download%20Latest%20Release-Here-brightgreen.svg)](https://github.com/NamMovies/OAuth2SecureService/releases)

Welcome to the **OAuth2SecureService** repository! This project implements a Spring Security 6.4.4 REST client microservice that acts as an OAuth2 Authorization Server. It operates over HTTPS and supports federated tokens, all while using PostgreSQL as the database backend. This README provides all the information you need to understand, install, and use this service effectively.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Introduction

OAuth2SecureService provides a secure way to manage user authentication and authorization. It allows client applications to request access tokens for users and enables resource servers to validate these tokens. The service is designed with security in mind, ensuring that sensitive data is handled safely.

## Features

- **OAuth2 Authorization Server**: Manage user authentication and token issuance.
- **Federated Tokens**: Support for tokens from multiple identity providers.
- **PostgreSQL Database**: Reliable and scalable database solution.
- **HTTPS Support**: Ensures secure data transmission.
- **Microservices Architecture**: Modular design for better scalability and maintenance.
- **Google Login Integration**: Simplifies user authentication with Google accounts.
- **Comprehensive API**: Easy to use for client applications.

## Technologies Used

- **Java**: The primary programming language.
- **Spring Security**: Framework for securing applications.
- **PostgreSQL**: The database management system.
- **OAuth2**: Protocol for authorization.
- **OIDC**: OpenID Connect for user authentication.
- **Microservices**: Architectural style for building applications as a collection of loosely coupled services.

## Getting Started

To get started with OAuth2SecureService, follow the installation and configuration steps below. You can download the latest release [here](https://github.com/NamMovies/OAuth2SecureService/releases). Make sure to check the "Releases" section for updates and new features.

## Installation

1. **Clone the Repository**:  
   Use the following command to clone the repository to your local machine:
   ```bash
   git clone https://github.com/NamMovies/OAuth2SecureService.git
   ```

2. **Navigate to the Project Directory**:  
   Change to the project directory:
   ```bash
   cd OAuth2SecureService
   ```

3. **Build the Project**:  
   Use Maven or Gradle to build the project. For Maven, run:
   ```bash
   mvn clean install
   ```

4. **Download Latest Release**:  
   Download the latest release from [here](https://github.com/NamMovies/OAuth2SecureService/releases) and execute the required files.

## Configuration

To configure the OAuth2SecureService, you will need to set up the application properties. This file is typically located in `src/main/resources/application.properties`. Here are the key properties to configure:

```properties
# Server Configuration
server.port=8080
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=yourpassword
server.ssl.keyStoreType=PKCS12

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/yourdbname
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

# OAuth2 Configuration
spring.security.oauth2.client.registration.google.client-id=your-client-id
spring.security.oauth2.client.registration.google.client-secret=your-client-secret
```

### Setting Up PostgreSQL

1. **Install PostgreSQL**:  
   Make sure PostgreSQL is installed on your machine.

2. **Create Database**:  
   Create a new database for the application:
   ```sql
   CREATE DATABASE yourdbname;
   ```

3. **Set Up User**:  
   Create a user and grant permissions:
   ```sql
   CREATE USER yourusername WITH PASSWORD 'yourpassword';
   GRANT ALL PRIVILEGES ON DATABASE yourdbname TO yourusername;
   ```

## Usage

Once the application is up and running, you can start using it to manage user authentication and authorization. The service exposes various endpoints for client applications to interact with.

### Starting the Application

Run the application using your IDE or through the command line with the following command:

```bash
java -jar target/OAuth2SecureService-1.0.0.jar
```

### Accessing the API

You can access the API endpoints using tools like Postman or cURL. Below are some common operations you can perform:

1. **Obtain Access Token**:  
   Send a POST request to the token endpoint:
   ```http
   POST /oauth/token
   ```

2. **Authenticate User**:  
   Use the `/login` endpoint to authenticate users.

3. **Access Protected Resources**:  
   Use the access token to access protected resources.

## API Endpoints

Here are some key API endpoints available in OAuth2SecureService:

| Method | Endpoint                      | Description                        |
|--------|-------------------------------|------------------------------------|
| POST   | `/oauth/token`                | Obtain an access token             |
| POST   | `/login`                      | Authenticate a user                |
| GET    | `/api/resource`               | Access protected resource           |
| GET    | `/api/user`                   | Get user details                   |

## Contributing

We welcome contributions to OAuth2SecureService! If you would like to contribute, please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and commit them.
4. Push to your fork and submit a pull request.

Please ensure that your code adheres to the existing style and includes tests where applicable.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For questions or support, please reach out to the maintainers:

- **Maintainer**: Your Name
- **Email**: your.email@example.com

For more information, visit the [Releases](https://github.com/NamMovies/OAuth2SecureService/releases) section to stay updated on new features and improvements.