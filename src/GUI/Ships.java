package GUI;

// Ship class representing a ship to place on the grid
class Ships {
    private int numberOfShips;
    int[] shipSizes;

    public Ships (int[] shipSizes) {
        this.shipSizes = shipSizes;
        this.numberOfShips = shipSizes.length;
    }

    public void setShipSizes(int[] shipSizes) {
        this.shipSizes = shipSizes;
        this.numberOfShips = shipSizes.length;
    }

    public int getNumberOfShips() {
        return numberOfShips;
    }

    public int[] getShipSizes() {
        return shipSizes;
    }
}