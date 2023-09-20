# Backend Repository README

## Introduction

This repository is dedicated to the backend services of our application. All backend services are currently running, but there is an important issue that needs to be addressed regarding Cross-Site Request Forgery (CSRF) protection in the `GlobalSecurity` class for both the `continue` and `login` functionalities.

## CSRF Vulnerability

- The application is currently vulnerable to CSRF attacks.
- The `GlobalSecurity` class needs to be updated to handle CSRF to ensure the security of our application.
- Until CSRF is properly handled, the Swagger documentation and endpoints will remain protected.

## Login Payload  `/users/save` 

- To log in, you can use the following payload   :{ "email":"admin@test.com", "password":1234 }    or run and add your custom login credential to be allowed to save in H2 [ users/save]


## Access to `/orders/all`

- Access to the `/orders/all` endpoint is enabled and allowed for all users.

## To-Do List
3. **Testing**: I need to add unit and integration tests.
4. **Docker Integration**: I've added a Dockerfile to the repository for containerization, but it has not been built yet. We should build and deploy our application using Docker for better manageability and scalability.



