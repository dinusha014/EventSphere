# EventSphere Ticket Service

The Ticket Service is responsible for generating, retrieving, cancelling, and deleting digital tickets for the EventSphere platform.

## Technology Stack

- Java
- Spring Boot
- MongoDB
- Swagger / OpenAPI
- API Key Security

## Port

8080

## API Key

Header:

X-API-KEY

Value:

EVENTSPHERE2026

## Endpoints

### Create Ticket

POST /api/tickets

### Get All Tickets

GET /api/tickets

### Get Ticket by ID

GET /api/tickets/{id}

### Get Ticket by Booking ID

GET /api/tickets/booking/{bookingId}

### Get Tickets by Customer Email

GET /api/tickets/customer?email={email}

### Cancel Ticket

PUT /api/tickets/{id}/cancel

### Delete Ticket

DELETE /api/tickets/{id}

## Swagger UI

http://localhost:8080/swagger-ui/index.html