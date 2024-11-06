package src.GameEngineBattleship;
import src.ALG.battleshipbot.Heatmap;
import src.ALG.battleshipbot.Positions;

public class gamemaster_battleship_v3{

    private int[] playerfield;
    private int[] opponentfield;
    private int[][] player_ship_locs;
    private int[] gridshape;
    private int[] ships;
    private boolean[] rules;
    private int ship_count;
    private Heatmap heatmap;
    // private int[] enemy_ship_locs; //TODO need this later, not for current build
    
    public gamemaster_battleship_v3(int [] gridshape, int[] ships, boolean[] rules){

        this.gridshape = gridshape;
        this.ships = ships;
        this.rules = rules;
        this.player_ship_locs = new int[ships.length][];
        this.playerfield = battleship_functions.create_player_field(gridshape);
        this.opponentfield = battleship_functions.create_player_field(gridshape);
        this.ship_count = 0;
        this.heatmap = Heatmap.createHeatmap(gridshape[1], gridshape[0], ships, Positions.PlacementRules.ALONGSIDE);
        heatmap.generate();
    }

    public int[] create_ship () {
        int[] ship = battleship_functions.create_random_ship(playerfield, gridshape, ships[ship_count], rules);
        ship_count++;
        return ship;
    }
    
    // heatmap interactie:
    public void hit (int hit_loc) {
        opponentfield[hit_loc] = 3;
        heatmap.hit(hit_loc);
        // enemy_ship_locs = custom_arrays.add_to_array(enemy_ship_locs, hit_loc);     // private int[] enemy_ship_locs; TODO need this later, not for current build
    }

    public void miss (int hit_loc) {
        opponentfield[hit_loc] = 1;
        heatmap.miss(hit_loc);
    }

    public void sink (int length) {     
        heatmap.sink(length);
    }

    public int getOptimalShot() {
        int shot = (int) heatmap.getOptimalShot();
        return shot;
    }

    public int[] getPlayerfield() {
        return playerfield;
    }
    public int[][] getPlayer_ship_locs() {
        return player_ship_locs;
    }
    public int[] getGridshape() {
        return gridshape;
    }
    public int[] getShips() {
        return ships;
    }
    public boolean[] getRules() {
        return rules;
    }

    public void print_playerfield() {
        battleship_functions.print_playerfield(playerfield, gridshape);
    }

    public void print_opponentfield() {
        battleship_functions.print_playerfield(opponentfield, gridshape);
    }

    public void print_heatmap() {
        long[] printHeatmap = heatmap.getHeatmap();
        battleship_functions.print_playerfield_long(printHeatmap, gridshape);
    }

}
