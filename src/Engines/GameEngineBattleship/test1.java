package src.Engines.GameEngineBattleship;
// Test1.java

import java.util.Arrays;

public class test1 {
    public static void main(String[] args) {
        int[] gridshape = {8, 8};
        int[] ships = {6, 4, 3, 2};
        boolean[] rules = {false};


        // int[] playerfield = battleship_functions.create_player_field(gridshape);
        // int[] ship_location = {0,0};
        // int size = 3;
        // boolean is_vertical = true;
        // boolean test = battleship_functions.check_shiplocation(playerfield, ship_location, size, is_vertical, gridshape, rules);
        // System.err.println(test);
        

        // Instantiate gamemaster_battleship_v3 from another file
        GameMasterBattleship gameMaster = new GameMasterBattleship(gridshape, ships, rules);
        System.out.println(Arrays.toString(gameMaster.placeRandomShip()));
        System.out.println(Arrays.toString(gameMaster.placeRandomShip()));
        System.out.println(Arrays.toString(gameMaster.placeRandomShip()));
        System.out.println(Arrays.toString(gameMaster.placeRandomShip()));
        int shot = (int)gameMaster.getOptimalShot();

        System.out.println(shot);
        gameMaster.miss(shot);

        shot = (int)gameMaster.getOptimalShot();
        System.out.println(shot);
        gameMaster.hit(shot);

        System.out.println("Game setup complete.");
        gameMaster.printPlayerField();
        System.out.println();
        gameMaster.printOpponentField();
        gameMaster.printHeatmap();

    }
} 




// public gamemaster_battleship_v3(int [] gridshape, int[] ships, boolean[] rules){
//     this.gridshape = gridshape;
//     this.ships = ships;
//     this.rules = rules;
//     this.player_ship_locs = battleship_functions.create_ships_randomly (battleship_functions.create_player_field(gridshape), gridshape, ships, rules);
//     this.playerfield = battleship_functions.add_ships_to_playerfield(battleship_functions.create_player_field(gridshape),player_ship_locs);

// }
