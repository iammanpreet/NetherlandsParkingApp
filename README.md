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

# Database Tables

## 1. ParkingRecord

- **Table Name:** parking_record
- **Description:** This table stores records of parking sessions.

| Column Name         | Data Type         | Description                          |
|---------------------|-------------------|--------------------------------------|
| parking_id          | BIGINT            | Primary key for the parking record.  |
| license_plate_number| VARCHAR(255)      | License plate number of the vehicle.  |
| street_id           | BIGINT            | Foreign key referencing `street` table.|
| start_time          | TIMESTAMP         | Start time of the parking session.   |
| end_time            | TIMESTAMP         | End time of the parking session.     |

## 2. Street

- **Table Name:** street
- **Description:** This table contains information about streets and their pricing.

| Column Name      | Data Type         | Description                          |
|------------------|-------------------|--------------------------------------|
| street_id        | BIGINT            | Primary key for the street.           |
| street_name      | VARCHAR(50)       | Name of the street.                   |
| price_per_min    | DECIMAL(10, 2)    | Price per minute for parking on the street.|

## 3. UnregisteredLicensePlate

- **Table Name:** `unregistered_license_plate`
- **Description:** This table stores observations of unregistered license plates.

| Column Name         | Data Type         | Description                          |
|---------------------|-------------------|--------------------------------------|
| unregistered_id     | BIGINT            | Primary key for the observation.      |
| license_plate_number| VARCHAR(255)      | License plate number of the vehicle.  |
| street_id           | BIGINT            | Foreign key referencing `street` table.|
| observation_date    | TIMESTAMP         | Date and time of the observation.    |
| is_reported         | VARCHAR(1)        | Indicates whether the observation is reported ('Y' or 'N').|

## 4. RegistrationObservationRecord

- **Table Name:** `registration_observation_record`
- **Description:** This table stores records of parking observations for registered license plates.

| Column Name         | Data Type         | Description                          |
|---------------------|-------------------|--------------------------------------|
| observation_id      | BIGINT            | Primary key for the observation record.|
| license_plate_number| VARCHAR(255)      | License plate number of the vehicle.  |
| street_id           | BIGINT            | Foreign key referencing `street` table.|
| observation_date    | TIMESTAMP         | Date and time of the observation.    |
| unregistered_id     | BIGINT            | Foreign key referencing `unregistered_license_plate` table.|


## Endpoints:
    Parking:
        POST /api/parking/registerParkingSession
        POST /api/parking/unregisterParkingSession
    Observations:
        POST /api/observation/saveRegistrationObservations
    Scheduled Task:
        Runs every 10 minutes to identify unregistered plates and generate reports.

## Health Check:
    Actuator url: http://localhost:8080/actuator/health
