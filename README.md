# EventSphere – Microservices-Based Event Booking Platform

EventSphere is a microservices-based event management and booking platform developed using Spring Boot, MongoDB, React, API Gateway, OAuth 2.0, API Key authentication, and Docker.

The system allows users to view events, create and manage bookings, generate digital tickets, and access ticket information through a React-based frontend. The backend is separated into independent microservices and all client requests are routed through a centralized API Gateway.

---

## Project Architecture

EventSphere consists of the following main components:

- Event Service
- Booking Service
- Ticket & Notification Service
- API Gateway
- React Frontend
- MongoDB
- Docker Compose

Basic request flow:

```text
React Frontend
      |
      v
API Gateway :8080
      |
      +-------------------+
      |         |         |
      v         v         v
 Event      Ticket     Booking
 Service    Service    Service
 :8081      :8082      :8083
      |         |         |
      +---------+---------+
                |
                v
             MongoDB
```

---

## Main Features

### Event Management

- Create events
- View available events
- View individual event details
- Update event information
- Delete events
- Store event information in MongoDB

### Booking Management

- Create event bookings
- View bookings
- Search bookings using customer information
- Calculate booking amounts
- Cancel bookings
- Maintain booking status

### Ticket Management

- Generate digital tickets
- Generate unique QR reference information
- View ticket information
- Search tickets by customer email
- Maintain ticket status
- Support ticket cancellation

### Frontend

- Responsive React user interface
- Event listing
- Event booking form
- Automatic booking summary calculation
- My Bookings page
- Booking cancellation
- Digital Tickets page
- Ticket QR information display
- Integration with backend microservices through the API Gateway

---

## Technologies Used

### Backend

- Java 17
- Spring Boot
- Spring Cloud Gateway MVC
- Spring Security
- OAuth 2.0
- Maven
- MongoDB

### Frontend

- React
- Vite
- JavaScript
- HTML5
- CSS3

### DevOps & Tools

- Docker
- Docker Compose
- Git
- GitHub
- Postman
- MongoDB Compass
- Visual Studio Code

---

## Microservices

| Component | Port | Responsibility |
|---|---:|---|
| API Gateway | 8080 | Central API routing, OAuth 2.0 and request security |
| Event Service | 8081 | Event management |
| Ticket Service | 8082 | Digital ticket management |
| Booking Service | 8083 | Event booking management |
| Frontend | 5173 | React user interface |
| MongoDB | 27017 | Data persistence |

---

## Security

EventSphere uses multiple security mechanisms.

### OAuth 2.0

The API Gateway provides OAuth 2.0 authentication using the Client Credentials grant.

The following scopes are configured:

```text
events.read
events.write
bookings.read
bookings.write
tickets.read
tickets.write
```

Protected requests require an access token:

```text
Authorization: Bearer <ACCESS_TOKEN>
```

### API Key Authentication

Individual microservices also validate API keys.

Example request header:

```text
X-API-KEY: <SERVICE_API_KEY>
```

This provides an additional security layer for protected service operations.

---

## API Gateway

All frontend and external API requests are routed through:

```text
http://localhost:8080
```

Main routes:

```text
/api/events/**
/api/bookings/**
/api/tickets/**
```

The Gateway routes requests to the appropriate microservice.

For local development, the default service addresses are:

```text
Event Service   -> http://localhost:8081
Ticket Service  -> http://localhost:8082
Booking Service -> http://localhost:8083
```

For Docker deployment, service names are used for internal container communication.

---

## Project Structure

```text
EventSphere/
│
├── api-gateway/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│
├── event-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│
├── booking-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│
├── ticket-service/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│
├── frontend/
│   ├── Dockerfile
│   ├── package.json
│   └── src/
│
├── docker-compose.yml
│
└── README.md
```

---

# Running the Project with Docker

Docker Compose is the recommended method for running the complete EventSphere platform.

## Prerequisites

Install:

- Docker Desktop
- Git

Make sure Docker Desktop is running before starting the application.

## 1. Clone the Repository

```bash
git clone <repository-url>
cd EventSphere
```

## 2. Build Docker Images

```bash
docker compose build
```

This builds Docker images for:

- API Gateway
- Event Service
- Booking Service
- Ticket Service
- React Frontend

## 3. Start the Platform

```bash
docker compose up -d
```

Docker Compose starts:

```text
eventsphere-mongodb
eventsphere-event-service
eventsphere-ticket-service
eventsphere-booking-service
eventsphere-api-gateway
eventsphere-frontend
```

## 4. Check Container Status

```bash
docker compose ps
```

All containers should display an `Up` status.

## 5. Access the Application

Frontend:

```text
http://localhost:5173
```

API Gateway:

```text
http://localhost:8080
```

## 6. Stop the Application

```bash
docker compose down
```

To rebuild the system after source-code changes:

```bash
docker compose up -d --build
```

---

# Running Without Docker

The services can also be started individually for development.

Recommended startup order:

```text
1. MongoDB
2. Event Service
3. Ticket Service
4. Booking Service
5. API Gateway
6. React Frontend
```

### Event Service

```bash
cd event-service
./mvnw spring-boot:run
```

Windows:

```cmd
mvnw.cmd spring-boot:run
```

### Ticket Service

```bash
cd ticket-service
./mvnw spring-boot:run
```

### Booking Service

```bash
cd booking-service
./mvnw spring-boot:run
```

### API Gateway

```bash
cd api-gateway
./mvnw spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

Then open:

```text
http://localhost:5173
```

---

# OAuth 2.0 Token Generation

OAuth tokens can be generated through the API Gateway.

Endpoint:

```http
POST http://localhost:8080/oauth2/token
```

Use HTTP Basic Authentication with the configured OAuth client credentials.

Request body:

```text
grant_type=client_credentials
```

Required scopes:

```text
events.read events.write bookings.read bookings.write tickets.read tickets.write
```

The returned access token should be included in protected API requests:

```text
Authorization: Bearer <ACCESS_TOKEN>
```

---

# Example API Endpoints

### Event Service

```http
GET    /api/events
POST   /api/events
GET    /api/events/{id}
PUT    /api/events/{id}
DELETE /api/events/{id}
```

### Booking Service

```http
GET  /api/bookings
POST /api/bookings
PUT  /api/bookings/{id}/cancel
```

### Ticket Service

```http
GET    /api/tickets
POST   /api/tickets
GET    /api/tickets/{id}
GET    /api/tickets/booking/{bookingId}
GET    /api/tickets/customer?email={email}
PUT    /api/tickets/{id}/cancel
DELETE /api/tickets/{id}
```

---

# Docker Configuration

Each application component contains its own Dockerfile.

Docker Compose provides:

- Containerized Spring Boot services
- Containerized React frontend
- MongoDB container
- Internal Docker networking
- Persistent MongoDB storage
- API Gateway service discovery using Docker service names

The complete platform can therefore be started using:

```bash
docker compose up -d
```

---

# Testing

The system was tested using Postman and the React frontend.

The following flows were verified:

- OAuth 2.0 token generation
- API key authentication
- Event retrieval
- Event creation and management
- Booking creation
- Booking lookup
- Booking cancellation
- Digital ticket generation
- Ticket retrieval
- Ticket QR reference generation
- API Gateway routing
- Docker service-to-service communication
- MongoDB persistence
- React frontend integration
- Complete Dockerized application flow

---

# Team Contributions

### W.G.N.N.Diwaman (ITBIN-2312-0013) – Event Service

Responsible for the Event Management microservice and related functionality.

### U.K.R.R.P.Ayuwardhana (ITBIN-2312-0020) – Booking Service

Responsible for the Booking Management microservice and booking-related functionality.

### W.H.C.D.J.Karunanayaka (ITBIN-2312-0021) – Ticket & Notification Service / Docker

Responsible for:

- Digital ticket functionality
- Ticket management
- QR reference generation
- Ticket-related functionality
- Dockerfiles
- Docker Compose
- Container integration
- Dockerized system testing

---

# Future Improvements

Possible future enhancements include:

- User registration and login
- Role-based access control
- Payment gateway integration
- Email and SMS notifications
- Advanced QR code validation
- Admin dashboard
- Event analytics
- Cloud deployment
- Automated CI/CD pipeline

---

## Conclusion

EventSphere demonstrates a microservices-based architecture for an event booking platform. The project separates event, booking, and ticket functionality into independent Spring Boot services and uses an API Gateway for centralized routing and security.

The React frontend communicates with the backend through the API Gateway, while MongoDB provides data persistence. Docker and Docker Compose allow the complete platform to be containerized and executed in a consistent environment.
