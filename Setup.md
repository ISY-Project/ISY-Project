# Hoe werkt het project?

1. GameMaster vraagt dmv de GUI, TicTacToe/Battleship

## TicTacToe

## Battleship

1. Setup Vragen:
    - Gamemode:
        - standaard:
            - board size: 0*10
            - ships: [5, 4, 3, 3, 2]
        - ISY:
            - board size: 8*8
            - ships: [6, 4, 3, 2]
        - custom:
            - board size: X*X
            - ships: [custom]
    - Connection:
        - Local
            - Vs mens of Computer.
            - Besturing kiezen.
        - Server
            - Altijd vs Computer.
            - Besturing altijd Computer
    - Besturing:
        - Zelf
        - Computer
2. Initialize:
    - Maak bord aan voor beide spelers.
3. Kies ship locaties:
    - Zelf:
        - Laat speler kiezen:
            - Locatie
            - Ship lengte
    - Computer:
        - Worden automatisch bepaalt.
4. Speler aan zet:
    - Schiet locatie:
        - Boem:
            - Registreer hit
        - Plons:
            - Registreer miss
    - Check winnaar/verliezer:
        - if winnaar > Meld winnaar en stop spel.
5. Verander Speler aan zet:
    - GoTo stap 4.
6. Computer aan zet:
    - Vraag locatie aan Alg:
