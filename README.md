# Project Title

Banking System.

## Description

A Spring Boot backend application for a banking system, implemented with Basic Auth security. 

For this application there are 2 levels of user: 

* USER (access to all Account Holder controller requests)
* ADMIN (access to all Admin controller requests)

The Third Party entity does not extend from the User class, so it is not included in the security dependencies.
For a visual overview of the application please refer to the attached Use Case and Class diagrams in this repository.

## Getting Started

### Setting up environment

* After pulling the project source code please include a resources root folder with the following application.properties file (src/main/resources/application.properties) with your mySQL username and password:

spring.datasource.url=jdbc:mysql://localhost:3306/banking?serverTimezone=UTC

spring.datasource.username=root (or another preferred user)

spring.datasource.password=YOURPASSWORD (for the given user)

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=create

spring.jpa.show-sql=true

### Installing

* For interacting with all HTTP requests, please install Postman: https://www.postman.com/downloads/
* If you would like to see all data created in the database, please open and create a schema called "banking" in mySQL Workbench (download and install: https://dev.mysql.com/downloads/workbench/)

### Executing program

* Execute java class file BankingSystemApplication: 2 User objects will be created -> TestAdmin (password "abcd") and TestUser(password "1234"). 	

* Head over to controllers, you will find different HTTP requests available under AccountHolderController, AdminController and ThirdPartyController

* Please refer to code comments under AccountHolderService, AdminService and ThirdPartyService for more details on the logic of the HTTP requests.

```
* AdminController (access "localhost:8080/admin", Basic Auth restricted to ADMIN role (Admin))

POST REQUEST("/create-account_holder") - to create an Account Holder.

POST REQUEST("/create-third_party")- to create a Third Party.

POST REQUEST("/create-checking") - to create an Account of type Checking (subclass).

POST REQUEST("/create-savings") - to create an Account of type Savings (subclass).

POST REQUEST("/create-credit_card") - to create an Account of type CreditCard (subclass).

GET REQUEST("/check-balance/{id}"} - to check balance of any Account.

PATCH REQUEST("/modify-balance/{id}"} - to modify balance of any Account.

DELETE REQUEST("/delete-account/{id}"} - to delete any Account.

```
```
* AccountHolderController (access "localhost:8080/user", Basic Auth restricted to USER role (Account Holder))

GET REQUEST("/primary_accounts") - to retrieve all primary accounts from logged-in Account Holder

GET REQUEST("/secondary_accounts") - to retrieve all secondary accounts from logged-in Account Holder.

GET REQUEST("/check-balance/{accountId"} - to check balance of an Account from logged-in Account Holder.

POST REQUEST("/transfer") - to create a Transaction from an Account from logged-in Account Holder..

```
```
* ThirdPartyController (access "localhost:8080", permitted to all)

POST REQUEST("/send") - to create a transaction to an existing Account, including "hashedkey" as a parameter in the header.

POST REQUEST("/receive") - to create a transaction from an existing Account, including "hashedkey" as a parameter in the header.

```

## Help

```
For reference on Spring Boot and the dependencies utilized in this project please check guidelines in file HELP.md.
```

## Authors

Victor Klemtz
[@vkap93](https://github.com/vkap93)

## Version History

* 0.1
    * Initial Release (pending full test coverage)

## License

This project is under the MIT License. Please refer to LICENSE.md for more details.

## Acknowledgments

This project is part of the Ironhack Java Bootcamp (Nov-Dec 2022). 
