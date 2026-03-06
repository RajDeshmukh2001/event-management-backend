# Event Management System
An Application where users can view and register for events while administrators can manage events.

---

## Technologies Used
- Java (17+)
- Spring Boot (3.0+)
- Spring Data JPA
- PostgreSQL (for production)
- JWT
- H2 Database (for testing)
- Gradle
- Postman (for API testing)

---

## How to Run the Project
### 1. Clone the Repository

```bash
git clone https://github.com/RajDeshmukh2001/event-management-backend.git
cd event-management-backend
```

### 2. Database Setup

#### Step 1: Install PostgreSQL
Download and install PostgreSQL from (https://www.postgresql.org/download/)

#### Step 2: Create Database using pgAdmin
1. Open pgAdmin
2. Connect to your PostgreSQL server
3. Create database name: customer_support_ticket_system

#### Step 3: Configure application.properties
- Main
```env
spring.application.name=event-management-backend
spring.datasource.url=jdbc:postgresql://localhost:5432/event_management_system
spring.datasource.username=postgres
spring.datasource.password=<YOUR_DB_PASSWORD>
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.defer-datasource-initialization=true
management.endpoints.web.exposure.include=health
management.endpoint.health.show-details=always
```
- Test
```env
spring.application.name=event-management-backend
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true
```
### 3. Start the Server
```bash
./gradlew run
```

### 4. The server will start on:
```bash
http://localhost:8080
```