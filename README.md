# COMP2130-Java-group-project

## Database Setup
1. Install PostgreSQL and DBeaver
2. Copy `src/main/resources/db/db.properties.sample` to `src/main/resources/db/db.properties`
3. Update `db.properties` with your PostgreSQL credentials
4. Open DBeaver and create a new PostgreSQL connection:
   - Host: localhost
   - Port: 5432
   - Username: postgres
   - Password: <input_your_own_password>
5. Create a new database named `hr_payroll_db`
6. Open and execute `src/main/resources/db/schema.sql` in DBeaver

## Running the Application
1. Open the project in IntelliJ IDEA
2. Wait for Maven to download dependencies
3. Run the `App.java` main class
