# NetherlandsParkingApp REST API

The Netherlands Parking System REST API is designed to manage parking sessions, registration observations, and generate reports for unregistered plates. This backend application is implemented using Java and Spring Boot.

## Table of Contents
- [Features](#features)
- [Setup](#setup)
- [Usage](#usage)
- [Endpoints](#endpoints)


## Features

- **Parking Entry:** Users can register their cars while entering parking and unregister their cars during exit.
- **Observation Records:** Admins can upload a list of license plates observed on the streets along with street details and observation date.
- **Scheduled Task:** Identifies unregistered plates, generates reports.

## Setup

**Clone Repository:**
   git clone https://github.com/iammanpreet/NetherlandsParkingApp.git
   Run mvn clean compile install

## Usage
    The Api is hosted on localhost:8080 with accessible url as : http://localhost:8080/api
    Swagger url for the API's: http://localhost:8080/swagger-ui/index.html

## Endpoints:
    Parking:
        POST /api/parking/registerParkingSession
        POST /api/parking/unregisterParkingSession
    Observations:
        POST /api/observation/saveRegistrationObservations
    Scheduled Task:
        Runs every 10 minutes to identify unregistered plates and generate reports.

##Health Check:
    Actuator url: http://localhost:8080/actuator/health
