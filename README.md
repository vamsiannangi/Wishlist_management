# Wishlist Management Application Setup and Documentation

## Overview

This application is a Spring Boot-based RESTful API designed for managing user wishlists. The development and testing of this project utilize Spring Boot and JUnit with Mockito. Additionally, it incorporates user authentication through Spring Security.

## Prerequisites

Ensure you have the following prerequisites installed on your machine:

- Java Development Kit (JDK)
- MySQL Database
- Git
- Maven

## Setup

1. **Clone the Repository:**

   ```bash
   git clone <repository-url>
   cd Wishlist-Management-Application
   ```

2. **Database Configuration:**

   - Create a MySQL database with the name `wishlists_management`.
   - Update the `application.properties` file with your database configuration:

      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/wishlists_management
      spring.datasource.username=root
      spring.datasource.password=password
      spring.jpa.hibernate.ddl-auto=update
      ```

3. **Build the Application:**

   ```bash
   mvn clean install
   ```


### Run the application Locally

The application will be accessible at `http://localhost:8080`. You can change the port in the `application.properties` file if needed.

## User Authentication

### Sign Up

To sign up a new user, send a POST request to:

```bash
method:POST 
url: http://localhost:8080/signup?username=<user>&password=<password>
```

### Log In

Use basic authentication to log in


## Wishlist Management API

### Get User's Wishlist

Retrieve the authenticated user's wishlist

provide your username and password in basic authentication
```bash
method:GET
url: http://localhost:8080/api/wishlists
```

### Add Item to Wishlist

Add an item to the authenticated user's wishlist

provide itemId which you want to add to wishlist

```bash
method:POST
url: http://localhost:8080/api/wishlists?itemId=1
```

### Delete Item from Wishlist

Delete an item from the authenticated user's wishlist:

provide itemId which you want to delete from wishlist
```bash
method: DELETE
url: http://localhost:8080/api/wishlists/{itemId}
```

## Unit Tests
To run the unit tests, navigate to the following path and execute the test files:

```bash
/Wishlist-Management/src/test/java/com/Wishlist/Management
```

## Conclusion

The Wishlist Management Application is now set up and ready for local testing and running.
