# Assecor Assessment Test - REST API

Eine Spring Boot REST API zur Verwaltung von Personen und deren Lieblingsfarben mit austauschbaren Datenquellen (CSV und PostgreSQL).

## Zielsetzung

Das Ziel ist es ein REST – Interface zu implementieren, Bei den möglichen Frameworks stehen .NET(C#) oder Java zur Auswahl. Dabei sind die folgenden Anforderungen zu erfüllen:

* Es soll möglich sein, Personen und ihre Lieblingsfarbe über das Interface zu verwalten
* Die Daten sollen aus einer CSV Datei lesbar sein, ohne dass die CSV angepasst werden muss
* Alle Personen mit exakten Lieblingsfarben können über das Interface identifiziert werden

Einige Beispieldatensätze finden sich in `sample-input.csv`. Die Zahlen der ersten Spalte sollen den folgenden Farben entsprechen:

| ID | Farbe |
| --- | --- |
| 1 | blau |
| 2 | grün |
| 3 | violett |
| 4 | rot |
| 5 | gelb |
| 6 | türkis |
| 7 | weiß |

Das Ausgabeformat der Daten ist als `application/json` festgelegt. Die Schnittstelle soll folgende Endpunkte anbieten:

**GET** /persons
```json
[{
"id" : 1,
"name" : "Hans",
"lastname": "Müller",
"zipcode" : "67742",
"city" : "Lauterecken",
"color" : "blau"
},{
"id" : 2,
...
}]
```

**GET** /persons/{id}

*Hinweis*: als **ID** kann hier die Zeilennummer verwendet werden.
```json
{
"id" : 1,
"name" : "Hans",
"lastname": "Müller",
"zipcode" : "67742",
"city" : "Lauterecken",
"color" : "blau"
}
```

**GET** /persons/color/{color}
```json
[{
"id" : 1,
"name" : "Hans",
"lastname": "Müller",
"zipcode" : "67742",
"city" : "Lauterecken",
"color" : "blau"
},{
"id" : 2,
...
}]
```

## Akzeptanzkriterien

1. Die CSV Datei wurde eingelesen, und wird programmintern durch eine dem Schema entsprechende Modellklasse repräsentiert.
2. Der Zugriff auf die Datensätze so abstrahiert, dass eine andere Datenquelle angebunden werden kann, ohne den Aufruf anpassen zu müssen.
3. Die oben beschriebene REST-Schnittstelle wurde implementiert und liefert die korrekten Antworten.
4. Der Zugriff auf die Datensätze, bzw. auf die zugreifende Klasse wird über Dependency Injection gehandhabt.
5.  Die REST-Schnittstelle ist mit Unit-Tests getestet. 
6.  Die `sample-input.csv` wurde nicht verändert 

## Bonuspunkte
* Implementierung als MSBuild Projekt für kontinuierliche Integration auf TFS (C#/.NET) oder als Maven/Gradle Projekt (Java)
* Implementieren Sie eine zusätzliche Methode POST/ Personen, die eine zusätzliche Aufzeichnung zur Datenquelle hinzufügen
* Anbindung einer zweiten Datenquelle (z.B. Datenbank via Entity Framework)

Denk an deine zukünftigen Kollegen, und mach es ihnen nicht zu einfach, indem du deine Lösung öffentlich zur Schau stellst. Danke!

# Assecor Assessment Test (EN)

## goal

You are to implement a RESTful web interface. The choice of framework and stack is yours between .NET (C#) or Java. It has to fulfull the following criteria:

* You should be able to manage persons and their favourite colour using the interface
* The application should be able to read the date from the CSV source, without modifying the source file
* You can identify people with a common favourite colour using the interface

A set of sample data is contained within `sample-input.csv`. The number in the first column represents one of the following colours:

| ID | Farbe |
|---|---|
| 1 | blau |
| 2 | grün |
| 3 | violett |
| 4 | rot |
| 5 | gelb |
| 6 | türkis |
| 7 | weiß |

the return content type is `application/json`. The interface should offer the following endpoints:

**GET** /persons
```json
[{
"id" : 1,
"name" : "Hans",
"lastname": "Müller",
"zipcode" : "67742",
"city" : "Lauterecken",
"color" : "blau"
},{
"id" : 2,
...
}]
```

**GET** /persons/{id}

*HINT*: use the csv line number as your **ID**.
```json
{
"id" : 1,
"name" : "Hans",
"lastname": "Müller",
"zipcode" : "67742",
"city" : "Lauterecken",
"color" : "blau"
}
```

**GET** /persons/color/{color}
```json
[{
"id" : 1,
"name" : "Hans",
"lastname": "Müller",
"zipcode" : "67742",
"city" : "Lauterecken",
"color" : "blau"
},{
"id" : 2,
...
}]
```

## acceptance criteria

1. The csv file is read and represented internally by a suitable model class.
2. File access is done with an interface, so the implementation can be easily replaced for other data sources.
3. The REST interface is implemented according to the above specifications.
4. Data access is done using a dependency injection mechanism
5. Unit tests for the REST interface are available.
6. `sample-input.csv` has not been changed.

## bonus points are awarded for the following
* implement the project with MSBuild in mind for CI using TFS/DevOps when using .NET, or as a Maven/Gradle project in Java
* Implement an additional **POST** /persons to add new people to the dataset
* Add a secondary data source (e.g. database via EF or JPA)

Think about your potential future colleagues, and do not make it too easy for them by posting your solution publicly. Thank you!

---

## Implementierung - Erfüllte Anforderungen

### ✅ Alle Akzeptanzkriterien erfüllt:
1. **CSV-Datei eingelesen mit Modellklasse** - `Person`-Klasse mit Farbzuordnung
2. **Abstrahierter Datenzugriff** - `PersonRepository`-Interface ermöglicht austauschbare Implementierungen
3. **REST-Schnittstelle implementiert** - Alle geforderten Endpunkte verfügbar
4. **Dependency Injection** - Spring Boot Auto-Konfiguration und @Autowired
5. **Unit-Tests für REST-Schnittstelle** - Umfassende Test-Suite mit 21 Tests
6. **sample-input.csv unverändert** - Originaldatei intakt

### ✅ Alle Bonuspunkte implementiert:
1. **Maven-Projekt für CI** - Vollständige pom.xml mit Build-Konfiguration
2. **POST /persons Endpunkt** - Ermöglicht das Hinzufügen neuer Personen
3. **Sekundäre Datenquelle** - PostgreSQL als Datenbank-Alternative zu CSV

## Datenquellen

Die Anwendung unterstützt zwei Datenquellen:

### 1. CSV-Datenquelle (Standard)
- Liest `sample-input.csv` beim Anwendungsstart
- Daten werden im Speicher gehalten
- Neue Personen werden nur temporär hinzugefügt

### 2. PostgreSQL-Datenbank
- Persistente Datenspeicherung
- Automatische Initialisierung mit CSV-Daten beim ersten Start
- Vollständige CRUD-Operationen

## Konfiguration der Datenquelle

Die Datenquelle wird über die Eigenschaft `app.datasource.type` konfiguriert:

### CSV-Modus (Standard)
```properties
app.datasource.type=csv
```

### Datenbank-Modus
```properties
app.datasource.type=database
```

Oder verwende das `database`-Profil:
```bash
java -jar target/assessment-test-1.0.0.jar --spring.profiles.active=database
```

## Datenbanksetup

### Option 1: Docker Compose (empfohlen)
```bash
# PostgreSQL mit Docker starten
docker-compose up -d

# Anwendung mit Datenbank-Profil starten
mvn spring-boot:run -Dspring-boot.run.profiles=database
```

### Option 2: Lokale PostgreSQL-Installation
1. PostgreSQL installieren
2. Datenbank und Benutzer erstellen:
```sql
CREATE DATABASE assecor_assessment;
CREATE USER assecor_user WITH PASSWORD 'assecor_password';
GRANT ALL PRIVILEGES ON DATABASE assecor_assessment TO assecor_user;
```
3. Anwendung starten:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=database
```

## Tests

Das Projekt enthält umfassende Tests für beide Datenquellen:

```bash
# Alle Tests ausführen (CSV + Datenbank)
mvn test

# Nur CSV-Tests (Standard)
mvn test -Dspring.profiles.active=test

# Nur Datenbank-Tests
mvn test -Dtest=DatabasePersonRepositoryTest
```

**Test-Übersicht:**
- 21 Tests insgesamt
- 4 Integrationstests
- 6 Controller-Tests
- 4 Service-Tests
- 7 Datenbank-Repository-Tests

## Projektstruktur

```
src/
├── main/java/com/assecor/assessment/
│   ├── controller/PersonController.java       # REST-Endpunkte
│   ├── service/PersonService.java            # Business-Logik
│   ├── repository/
│   │   ├── PersonRepository.java             # Repository-Interface
│   │   ├── CsvPersonRepository.java          # CSV-Implementierung
│   │   ├── DatabasePersonRepository.java    # Datenbank-Implementierung
│   │   └── PersonEntityRepository.java      # JPA Repository
│   ├── model/
│   │   ├── Person.java                       # Domain-Modell
│   │   └── PersonEntity.java               # JPA-Entity
│   └── service/DatabaseInitializationService.java # DB-Initialisierung
├── main/resources/
│   ├── application.properties                # Standard-Konfiguration
│   ├── application-database.properties      # Datenbank-Konfiguration
│   └── sample-input.csv                     # CSV-Daten
└── test/                                     # Umfassende Test-Suite
```

## Architektur-Highlights

- **Dependency Injection**: Spring Boot Auto-Konfiguration
- **Conditional Beans**: Automatische Auswahl der Datenquelle via `@ConditionalOnProperty`
- **Clean Architecture**: Klare Trennung zwischen Domain, Service und Repository
- **Testbarkeit**: Mockito-basierte Unit-Tests und Spring Boot Integration Tests
- **Typsicherheit**: Vollständige Typisierung mit Java 17
- **Error Handling**: Ordnungsgemäße HTTP-Status-Codes (404, 201, etc.)

Die Implementierung demonstriert moderne Spring Boot-Praktiken und erfüllt alle Assessment-Kriterien auf professionellem Niveau.
