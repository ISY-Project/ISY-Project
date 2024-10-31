package src.GUI;

// Ship class representing a ship to place on the grid
// TODO: Update the amount of ships available after we placed. (Communicate with engine.)
class Ships {
    private final int numberOfShips;
    private final int[] shipSizes;
    Ship[] ships;

    public Ships (int[] shipSizes) {
        this.shipSizes = shipSizes;
        this.numberOfShips = shipSizes.length;
        this.ships = new Ship[numberOfShips];
        for (int i = 0; i < numberOfShips; i++) {
            ships[i] = new Ship(shipSizes[i]);
        }
    }

    public int[] ShipSizes() {
        return shipSizes;
    }

    public int NumberOfShips() {
        return numberOfShips;
    }

    public Ship getShip(int index) {
        return ships[index];
    }

    public Ship[] getShips() {
        return ships;
    }
    
    public Ship getShipBySize(int selectedShipSize) {
        Ship selectedShip = null;
        for (var ship : ships) {
            if (ship.getSize() == selectedShipSize) {
                selectedShip = ship;
            }
        }
        return selectedShip;
    }

}