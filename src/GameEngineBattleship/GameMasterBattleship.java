package src.GameEngineBattleship;
import src.ALG.battleshipbot.Heatmap;
import src.ALG.battleshipbot.Positions;

public class GameMasterBattleship{
    private int[] playerField;
    private int[] opponentField;
    private int[][] playerShipLocations;
    private int[] gridShape;
    private int[] ships;
    private boolean[] rules;
    private int ship_count;
    private Heatmap heatmap;
    // private int[] enemy_ship_locs; //TODO need this later, not for vrijdag/comp build
    
    public long[] getHeatmap() {
        return heatmap.getHeatmap();
    }

    public GameMasterBattleship(int [] gridShape, int[] ships, boolean[] rules){

        this.gridShape = gridShape;
        this.ships = ships;
        this.rules = rules;
        this.playerShipLocations = new int[ships.length][];
        this.playerField = battleship_functions.create_player_field(gridShape);
        this.opponentField = battleship_functions.create_player_field(gridShape);
        this.ship_count = 0;
        this.heatmap = Heatmap.createHeatmap(gridShape[1], gridShape[0], ships, Positions.PlacementRules.ALONGSIDE);
        heatmap.generate();
    }

    public int[] placeRandomShip () {
        int[] ship = battleship_functions.create_random_ship(playerField, gridShape, ships[ship_count], rules);
        ship_count++;
        return ship;
    }
    
    // heatmap interactie:
    public void hit (int hit_loc) {
        opponentField[hit_loc] = 3;
        heatmap.hit(hit_loc);
        // enemy_ship_locs = custom_arrays.add_to_array(enemy_ship_locs, hit_loc);     // private int[] enemy_ship_locs; TODO need this later, not for current build
    }

    public void miss (int hit_loc) {
        opponentField[hit_loc] = 1;
        heatmap.miss(hit_loc);
    }

    public void sink (int hit_loc, int length) {
        heatmap.hit(hit_loc);
        heatmap.sink(length);
    }

    public long getOptimalShot() {
        return heatmap.getOptimalShot();
    }

    public int[] getPlayerField() {
        return playerField;
    }
    public int[][] getPlayerShipLocations() {
        return playerShipLocations;
    }
    public int[] getGridShape() {
        return gridShape;
    }
    public int[] getShips() {
        return ships;
    }
    public boolean[] getRules() {
        return rules;
    }

    public void printPlayerField() {
        battleship_functions.print_playerfield(playerField, gridShape);
    }

    public void printOpponentField() {
        battleship_functions.print_playerfield(opponentField, gridShape);
    }

    public void printHeatmap() {
        long[] printHeatmap = heatmap.getHeatmap();
        battleship_functions.print_playerfield_long(printHeatmap, gridShape);
    }

}
