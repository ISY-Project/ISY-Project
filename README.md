# ISY-Project

## how to compile

1. Clone the repository: `git clone https://github.com/ISY-Project/ISY-Project.git`
2. Cd into the project directory: `cd ./ISY-Project`
3. Compile the java project: `javac -cp src/Main.java -d build/classes`
4. Compile the java project into a .jar file: `jar cfe build\jar\BitShifters-vx.x.x.jar org.bitshifters.gameclient.Main -C build\classes .`


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
