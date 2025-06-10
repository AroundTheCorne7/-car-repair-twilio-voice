# Car Repair Shop Booking System

A Spring Boot 3 REST API application for managing car repair shop appointments with multiple mechanics who have different skills.

## Features

- **Mechanic Management**: Create, update, and manage mechanics with their skills
- **Time Slot Management**: Generate and manage available time slots for mechanics
- **Booking System**: Book appointments with skill validation and availability checks
- **Service Types**: Support for different service types (DIAGNOSTICS, ALIGNMENT, OIL_CHANGE, BRAKE_REPAIR, TIRE_CHANGE)
- **Business Rules**: Automatic validation of mechanic skills, time slot availability, and overlap prevention
- **REST API**: Full REST API with Swagger documentation
- **Database**: PostgreSQL with JPA/Hibernate

## Technology Stack

- **Java 17+**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **Swagger/OpenAPI 3**
- **H2 Database** (for testing)

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+ running on localhost:5432
- Database named `car_repair_shop`

## Database Setup

1. Install and start PostgreSQL
2. Create a database named `car_repair_shop`:
   ```sql
   CREATE DATABASE car_repair_shop;
   ```
3. Update the database credentials in `src/main/resources/application.properties` if needed:
   ```properties
   spring.datasource.username=postgres
   spring.datasource.password=postgres
   ```

## Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. The application will start on `http://localhost:8080`

## API Documentation

Once the application is running, you can access:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs

## API Endpoints

### Mechanics (`/api/mechanics`)
- `GET /` - List all mechanics
- `POST /` - Create new mechanic with skills
- `PATCH /{id}` - Update mechanic's name or skills
- `DELETE /{id}` - Delete mechanic
- `GET /by-skill?skill=DIAGNOSTICS` - Get mechanics by skill
- `GET /search?name=John` - Search mechanics by name

### Time Slots (`/api/timeslots`)
- `GET /available?serviceType=DIAGNOSTICS` - List available slots for service type
- `POST /generate` - Generate slots for a mechanic over a date range
- `PATCH /{id}` - Update a timeslot
- `DELETE /{id}` - Delete a timeslot
- `GET /mechanic/{mechanicId}/available` - Get available slots for mechanic

### Bookings (`/api/bookings`)
- `POST /` - Create booking
- `GET /` - List all bookings
- `DELETE /{id}` - Cancel/delete a booking
- `GET /mechanic/{mechanicId}` - Get bookings by mechanic
- `GET /service-type/{serviceType}` - Get bookings by service type
- `GET /search?customerName=John` - Search bookings by customer name

## Example Usage

### 1. Create a Mechanic
```bash
curl -X POST http://localhost:8080/api/mechanics \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Smith",
    "skills": ["DIAGNOSTICS", "BRAKE_REPAIR", "OIL_CHANGE"]
  }'
```

### 2. Generate Time Slots
```bash
curl -X POST http://localhost:8080/api/timeslots/generate \
  -H "Content-Type: application/json" \
  -d '{
    "startDate": "2024-01-15",
    "endDate": "2024-01-19",
    "workingStartTime": "09:00:00",
    "workingEndTime": "17:00:00",
    "slotDurationMinutes": 60,
    "mechanicId": 1
  }'
```

### 3. Create a Booking
```bash
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "Jane Doe",
    "phoneNumber": "+1234567890",
    "description": "Car making strange noises",
    "serviceType": "DIAGNOSTICS",
    "mechanicId": 1,
    "timeSlotId": 1
  }'
```

## Business Rules

1. **Skill Validation**: A mechanic must have the requested serviceType in their skills to be booked
2. **Availability**: Bookings are only allowed for available slots
3. **No Overlaps**: Time slots cannot overlap per mechanic
4. **Automatic Updates**: After booking, the slot becomes unavailable
5. **Past Validation**: Cannot book time slots in the past

## Testing

Run the tests with:
```bash
mvn test
```

The tests use an in-memory H2 database and don't require PostgreSQL to be running.

## Project Structure

```
src/main/java/com/anykeysolutions/
├── entity/          # JPA entities
├── repository/      # Spring Data repositories
├── service/         # Business logic services
├── controller/      # REST controllers
├── dto/            # Data Transfer Objects
├── config/         # Configuration classes
└── CarRepairShopApplication.java
```

## Configuration

Key configuration properties in `application.properties`:

- Database connection settings
- JPA/Hibernate configuration
- Swagger/OpenAPI settings
- Logging configuration

## Frontend Application

This project includes a modern React frontend located in the `frontend/` directory.

### Frontend Features
- **Customer Interface**: Service booking with real-time availability
- **Admin Dashboard**: Complete management system for mechanics, time slots, and bookings
- **Responsive Design**: Works on desktop, tablet, and mobile devices
- **Material Design**: Clean, professional UI using Material-UI components

### Running the Frontend

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm start
   ```

4. Open http://localhost:3000 in your browser

The frontend will automatically proxy API requests to the backend running on port 8080.

### Frontend Technology Stack
- React 18 with hooks
- Material-UI (MUI) for components
- React Router for navigation
- React Hook Form for form management
- Axios for API communication
- Date-fns for date handling

For detailed frontend documentation, see `frontend/README.md`.

## Full Application Setup

To run the complete application:

1. **Start the Backend**:
   ```bash
   # In the root directory
   mvn spring-boot:run
   ```

2. **Start the Frontend** (in a new terminal):
   ```bash
   cd frontend
   npm install
   npm start
   ```

3. **Access the Application**:
   - Frontend: http://localhost:3000
   - Backend API: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui.html

## License

This project is licensed under the MIT License.
