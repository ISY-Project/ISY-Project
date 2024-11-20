# ISY-Project

## Prerequisites

- Java 23 SDK - [download here](https://www.oracle.com/java/technologies/downloads/)
- Apache Maven 3.9.9 - [download here](https://maven.apache.org/download.cgi)
- JavaFX SDK 23.0.1 - [download here](https://gluonhq.com/products/javafx/)

## How to run

### Via maven

1. Clone the repository: `git clone https://github.com/ISY-Project/ISY-Project.git`
2. Cd into the project directory: `cd ./ISY-Project`
3. To run the project uses `mvn clean javafx:run`

### Via VSCode

1. Clone the repository: `git clone https://github.com/ISY-Project/ISY-Project.git`
2. Cd into the project directory: `cd ./ISY-Project`
3. Copy `launch.json.example` to `launch.json`
4. Edit the `launch.json` to use the JavaFX SDK by replacing `<path to JavaFX SDK lib version 23.0.1>` with the path to your JavaFX SDK installation.
5. Use the VSCode run option in order to run the program.

## How to compile

1. Clone the repository: `git clone https://github.com/ISY-Project/ISY-Project.git`
2. Cd into the project directory: `cd ./ISY-Project`
3. Compile the java project: `mvn clean compile`
4. Compile the java project into a .jar file: `mvn package`
5. Run the .jar file: `java -jar target/BitShifters-full-vX.X.X-alpha-jar-with-dependencies.jar [-options] [arguments]`
6. Verify the installation by running the following command: `java -jar target/BitShifters-full-vX.X.X-alpha-shaded.jar --help`
7. You can run the file with the paramaters set in an file by adding `@.\<parameters_file>` as the only argument
   - Every argument should be separated with an new line, this includes the parameter and value (look at `run_parameters`)
   - Other arguments can be added, but it may result in conflicts (adding `-debug` twice turns debug back off)

## Regels

Voor het ontwikkelen van Zeeslagje willen we graag dat de volgende spelregels worden gebruikt:

Spelbord 8x8 (ipv 10x10)
4 schepen:

- 1x Lengte 6
- 1x Lengte 4
- 1x Lengte 3
- 1x Lengte 2

Schepen niet tegen elkaar aan.
Schepen wel tegen de rand.
Om de beurt een zet, random wie begint.

## TODO

Houdt functioneel en technisch ontwerk gescheiden.
