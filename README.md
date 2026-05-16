# LocationVoiture 🚗

A Java desktop application for managing car rentals.

## Technologies
- Java (Eclipse IDE)
- SQLite (via JDBC)

## Project Structure
LocationVoiture/
├── src/        # Java source files
├── lib/        # External libraries (JARs)
├── bin/        # Compiled classes (ignored by git)
└── init.sql    # Database schema and initial data
## Getting Started

### Prerequisites
- Java JDK 8+
- Eclipse IDE
- [DB Browser for SQLite](https://sqlitebrowser.org/) *(optional)*

### Setup
1. Clone the repository
```bash
   git clone https://github.com/BLUX33/location-voiture.git
```

2. Import into Eclipse
   - **File → Import → Existing Projects into Workspace**
   - Select the cloned folder

3. Create the database
```bash
   sqlite3 LocationVoiture.db < init.sql
```

4. Run the app
   - Right-click `Main.java` → **Run As → Java Application**

## Features
- Add / edit / delete cars
- Manage reservations
- Track clients

## Author
BLUX33
