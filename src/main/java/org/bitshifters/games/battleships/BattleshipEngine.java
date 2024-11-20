package org.bitshifters.games.battleships;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bitshifters.games.components.GridEngine;
import org.bitshifters.games.components.Player;


public class BattleshipEngine extends GridEngine<BattleshipCell> {
    private final HashMap<Player, List<Ship>> placedShips;
    private final ArrayList<Integer> validShipLengths;
    public boolean noSurroundingShips = true;
    private final int rows;
    private final int cols;

    public BattleshipEngine(final boolean noSurroundingShips, final int rows, final int cols, final ArrayList<Integer> validShipLengths) {
        this.rows = rows;
        this.cols = cols;
        this.noSurroundingShips = noSurroundingShips;
        this.validShipLengths = validShipLengths;
        this.grids = new HashMap<>();
        this.placedShips = new HashMap<>();
    }

    public void placeShip(final int row, final int col, final int length, final boolean horizontal, final Player player) {
        addShip(new Ship(row, col, length, horizontal), player);
        for (int i = 0; i < length; i++) {
            final var x = horizontal ? row : row + i;
            final var y = horizontal ? col + i : col;
            setCell(x, y, BattleshipCell.SHIP, player);
        }
    }

    /**
     * Validate a single cell for a ship being placed.
     */
    private boolean validatePlacementCell(final int row, final int col, final Player player) {
        // Not placed before
        // if (placedShips.containsKey(player)) {
        //     return false;
        // }
        // Not outside the grid
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return false;
        }
        var cell = getCell(row, col, player);
        if (cell.contains(BattleshipCell.SHIP)) {
            return false;
        }
        if (noSurroundingShips) {
            if (row != rows - 1) {
                cell = getCell(row + 1, col, player); 
                if (cell.contains(BattleshipCell.SHIP)) {
                    return false;
                }
            }
            if (col != cols - 1) {
                cell = getCell(row, col + 1, player);
                if (cell.contains(BattleshipCell.SHIP)) {
                    return false;
                }
            }
            if (row != 0){
                cell = getCell(row - 1, col, player);
                if (cell.contains(BattleshipCell.SHIP)) {
                    return false;
                }
            }
            if (col != 0) {
                cell = getCell(row, col - 1, player);
                if (cell.contains(BattleshipCell.SHIP)) {
                    return false;
                }
            }
        }
        return true;
    }

    private int countShipOccurrences(final Player player, final int length) {
        int count = 0;
        for (final Ship ship : placedShips.get(player)) {
            if (ship.getLength() == length){
                count++;
            }
        }
        return count;
    }

    private int countIntOccurrences(final List<Integer> elements, final int target) {
        int count = 0;
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).equals(target)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Validate the ship placement for the player.
     */
    public boolean validateShipPlacement(final int row, final int col, final int length, final boolean horizontal, final Player player) {
        if (countShipOccurrences(player, length) == countIntOccurrences(validShipLengths, length)) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (horizontal){
                if (!validatePlacementCell(row, col + i, player)) {
                    return false;                    
                }
            } else {
                if (!validatePlacementCell(row + i, col, player)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean allShipsPlaced(final Player player) {
        return placedShips.get(player).size() == validShipLengths.size();
    }

    public boolean allShipsSunk(final Player player) {
        for (final Ship ship : placedShips.get(player)) {
            if (!ship.isSunk()){
                return false;
            }
        }
        return true;
    }

    public boolean validateMove(final int row, final int col, final Player player) {
        return validateShot(rows, cols, activePlayer);
    }

    /**
     * Validate the shot location for the player.
     */
    public boolean validateShot(final int row, final int col, final Player player) {
        final var cell = getCell(row, col, player);
        if (cell == BattleshipCell.HIT || cell == BattleshipCell.MISS) {
            return false;
        }
        return true;
    }

    /**
     * Mark a given location as hit.
     * @param row
     * @param col
     * @param player
     */
    public void markHit(final int row, final int col, final Player player) {
        setCell(row, col, BattleshipCell.HIT, player);
    }

    // TODO moet schot verwerken
    public BattleshipCell shot(final int row, final int col, final Player player) {
        if (getCell(row, col, player) == BattleshipCell.EMPTY) {
            setCell(row, col, BattleshipCell.MISS, player);
        } else if (getCell(row, col, player) == BattleshipCell.SHIP) {
            setCell(row, col, BattleshipCell.HIT, player);
            for (final Ship ship : placedShips.get(player)) {
                if (ship.isHit(row, col)){
                    ship.hit();
                }
            }
        }
        return getCell(row, col, player);
    }


    public void addShip(final Ship ship, final Player player) {
        var playerShips = placedShips.get(player);
        if (playerShips == null) {
            playerShips = new ArrayList<>();
            placedShips.put(player, playerShips);
        } else {
            playerShips.add(ship);
        }
    }

    public boolean hasPlacedAllShips(final Player player) {
        return placedShips.get(player).size() == validShipLengths.size();
    }

    @Override
    public boolean isGameOver() {
        if (getWinner() != null) {
            return true;
        }
        return false;
    }

    @Override
    public Player getWinner() {
        for (final Player player : placedShips.keySet()) {
            if (allShipsSunk(player)) {
                return player;
            }
        }
        return null;
    }



// ----------------------------------------------------------------------------------------------------------------------------



    // Method to create a array filled with zeros
    
//     private static int[] create_player_field(int[] gridshape) {
//         int length = gridshape[0]*gridshape[1];
//         int[] battlefield = new int[length];
//         return battlefield;
//     }

//     private static int location(int[] gridshape, int[] ship_location){
//         return gridshape[1]*ship_location[0]+ship_location[1];
//     }

//     private static boolean vertical_check_ship_fit_on_grid (int[] playerfield, int[] gridshape, int[] ship_location, int size){
//         int[] test = {ship_location[0] + size - 1, ship_location[1]};
//         int check1 = location(gridshape, test);
//         int check2 = gridshape[0]*gridshape[1];
//         if (check1>=check2) {

//             return false;
//         }
//         return true;
//     }

//     private static boolean horizontal_check_ship_fit_on_grid (int[] playerfield, int[] gridshape, int[] ship_location, int size){
//         int check1 = ship_location[1] + size - 1;
//         if (check1 >= gridshape[1]) {
//             return false;
//         } 
//         return true;
//     }

//     private static boolean check_shiplocation_no_rules (int[] playerfield, int ship_loc, int size, boolean is_vertical, int[] gridshape){
//         int test = 0;
//         if (is_vertical) {
//             for (int i = 0; i < size; i++){
//                 test = test + playerfield[i * gridshape[1] + ship_loc];
//             }
//         } else {
//             for (int i = 0; i < size; i++) {
//                 test = test + playerfield[ship_loc + i];
//             }
//         }  
//         if (test == 0 ){
//             return true;
//         } else {
//             return false;
//         }

//     }

//     private static boolean check_ship_location_alongside (int[] playerfield, int[] ship_location, int size, boolean is_vertical, int[] gridshape) {
//         // als die verticaal is:
//         int ifnottop = 1;
//         int ifnotbottom = 1;
//         int ifnotleft = 1;
//         int ifnotright = 1;
//         int test = 0;
//         int ship_loc = location(gridshape, ship_location);
//         // check if touching top
//         if (ship_location[0] == 0) {
//             ifnottop = 0;
//         }
        
//         // check if touching left
//         if (ship_location[1] == 0) {
//             ifnotleft = 0;
//         }
//         // checkt verticaal
//         if (is_vertical) {
//             // check if touching right
//             if (ship_location[1] == gridshape[1] - 1){
//                 ifnotright = 0;
//             }

//             // check if touching bottom
//             if (ship_location[0] + size - 1 == gridshape[0] - 1) {
//                 ifnotbottom = 0;
//             }
//             for (int i=0; i < size; i++){
//                 int test_loc = i * gridshape[1] + ship_loc;
//                 test = test + sumSlice(playerfield, test_loc - ifnotleft, test_loc + ifnotright + 1);
//             }
//             if (ifnottop == 1) {
//                 test = test + playerfield[ship_loc - gridshape[1]];
//             }

//             if (ifnotbottom == 1) {
//                 test = test + playerfield[ship_loc + gridshape[1] * size];
//             }
//         // checkt horizontaal
//         } else {
//             // check if touching right
//             if (ship_location[1] + size - 1 == gridshape[1] - 1) {
//                 ifnotright = 0;
//             }
//             // check if touching bottom
//             if (ship_location[0] == gridshape[0] - 1) {
//                 ifnotbottom = 0;
//             }
//             for (int i=0-ifnottop; i <1+ifnotbottom; i++){
//                 int test_loc = i * gridshape[1] + ship_loc;
//                 test = test + sumSlice(playerfield, test_loc, test_loc + size);
//             }
//             if (ifnotleft == 1) {
//                 test = test + playerfield[ship_loc - 1];
//             }

//             if (ifnotright == 1) {
//                 test = test + playerfield[ship_loc + size]; // + 1 - 1 valt weg
//             }

//         }
//         if (test == 0){
//             return true;
//         } else {
//             return false;
//         }
//     }

//     private static boolean check_shiplocation(int[] playerfield, int[] ship_location, int size, boolean is_vertical, int[] gridshape, boolean[] rules) {
//         int ship_loc= location(gridshape, ship_location);        

//         // check if shape lies in grid
//         boolean result = false;
//         if (is_vertical) {
//             result = vertical_check_ship_fit_on_grid( playerfield, gridshape,  ship_location,  size);
//         } else {
//             result = horizontal_check_ship_fit_on_grid (playerfield, gridshape, ship_location, size);
//         }   
//         if (result == false) {
//             return false;
//         }
//         // check if ship location is valid with the rules that ships are allowed to touch

//         if (rules[0] && check_shiplocation_no_rules(playerfield, ship_loc, size, is_vertical, gridshape) == false) {  
//             return  false;                     
//         } else {
//             return check_ship_location_alongside (playerfield, ship_location, size, is_vertical, gridshape);
//         }
//     }


//     private static int[] place_ship(int[] playerfield, int[] ship) {
//         for (int i = 0; i < ship.length; i++){
//             playerfield[ship[i]] = 2;
//         }
//         return playerfield;
//     }

//     private static int[] create_ship(int[] ship_location, int size, int[] gridshape, boolean is_vertical) {
//         int ship_loc = location(gridshape, ship_location);
//         int[] ship = new int[size];

//         if (is_vertical){
//             for (int i=0; i < size ; i++){
//                 ship[i] = ship_loc + i * gridshape[1];
//             }            
//         } else {
//             for (int i=0; i < size ; i++){
//                 ship[i] = ship_loc + i;
//             }
//         }
//         return ship;
//     }


//     private static void print_playerfield(int[] playerfield, int[] gridshape) {
//         System.out.print("[");
//         for (int i = 0; i < gridshape[0]; i++) {
//             for (int j = 0; j < gridshape[1]; j++){
//                 System.out.print(playerfield[j + i * gridshape[0]]);
//                 if (j != gridshape[1] - 1 || i != gridshape[1] - 1) {
//                     System.out.print(", ");
//                 } else {
//                     System.out.print("]");
//                 }
            
//             }
//         System.out.println();
//         }
//         System.out.println();
//     }

//     private static void print_playerfield_long (long[] playerfield, int[] gridshape) {
//         System.out.print("[");
//         for (int i = 0; i < gridshape[0]; i++) {
//             for (int j = 0; j < gridshape[1]; j++){
//                 System.out.print(playerfield[j + i * gridshape[0]]);
//                 if (j != gridshape[1] - 1 || i != gridshape[1] - 1) {
//                     System.out.print(", ");
//                 } else {
//                     System.out.print("]");
//                 }
            
//             }
//         System.out.println();
//         }
//         System.out.println();
//     }

//     private static boolean check_valid_shot(int[] playerfield, int shot_loc) {
//         if (playerfield[shot_loc] == 0 || playerfield[shot_loc] == 3) {
//             return true;
//         }
//         return false;
//     }
//     private static int[] find_enemy_ship(int[] connector_ship_locs, int [] gridshape) {
//         int[] found_ship_locs = {};
//         int i=0; 
//         Arrays.sort(connector_ship_locs);
//         // TODO: kijk of de twee vervangen kan worden met een minimum ship length
//         // vindt horizontale schepen
//         while (found_ship_locs.length < 2 && i < connector_ship_locs.length) {
//             found_ship_locs = find_next_enemy_ship_horizontal(connector_ship_locs[i], Arrays.copyOfRange(connector_ship_locs, i, connector_ship_locs.length), gridshape);
//             i++;
//         }
//         // vindt verticale schepen
//         i=0;
//         while (found_ship_locs.length < 2 && i < connector_ship_locs.length - 1) {
//             found_ship_locs = find_next_enemy_ship_vertical(connector_ship_locs[i], Arrays.copyOfRange(connector_ship_locs, i, connector_ship_locs.length), gridshape);
//             i++;
//         }
//         return found_ship_locs;
//     }

//     private static int[] find_next_enemy_ship_horizontal (int ship_loc, int[] connector_ship_locs, int [] gridshape){
//         int [] result = {ship_loc};
//         int i=0;
//         boolean next = true;
    
//         while (next) {
//             if (ship_loc + 1 == connector_ship_locs[0] && connector_ship_locs[0] % gridshape[1] !=0 ){
//                 result = add_to_array(result, connector_ship_locs[i]);
//             } else {
//                 next = false;
//             }
//             i++;
//         }
//         return result;

// }   

//     private static int[] find_next_enemy_ship_vertical (int ship_loc, int[] connector_ship_locs, int [] gridshape) {
//         int [] result = {ship_loc};
//         int i=0;
//         boolean next = true;
        
//         while (next) {
//             if (ship_loc + gridshape[1] == connector_ship_locs[0]){
//                 result = add_to_array(result, connector_ship_locs[i]);
//             } else {
//                 next = false;
//             }
//             i++;
//         }
//         return result;
//     }

//     private static int[] create_random_ship (int[] playerfield, int [] gridshape, int ship_length, boolean[] rules){
//         int [] player_ship = new int[ship_length];
//         int max_random_int_row = gridshape[0];
//         int max_random_int_column = gridshape[1];

//         Random random = new Random();
//         int[] ship_location = new int[2]; // array is twee groot omdat het grid in twee dimenties is 
//         boolean is_vertical = false;
//         boolean test_location = false;


//         while (test_location == false) {
//             // TODO add the next ship with UI
//             ship_location[0] = random.nextInt(max_random_int_row);
//             ship_location[1] = random.nextInt(max_random_int_column);
//             is_vertical = random.nextBoolean();
//             test_location = check_shiplocation(playerfield, ship_location, ship_length,is_vertical, gridshape, rules);
//         }
//         //     private static int[] create_ship(int[] ship_location, int size, int[] gridshape, boolean is_vertical) {
//         player_ship = create_ship(ship_location, ship_length, gridshape, is_vertical);
//         playerfield = place_ship(playerfield, player_ship);

//         return player_ship;
//     }

//     private static int[] add_ships_to_playerfield (int[] playerfield, int[][] ships) {
//         for (int i = 0; i < ships.length; i++){
//             for (int j = 0; j < ships[i].length; j++){
//                 playerfield[ships[i][j]] = 2;
//             }
//         }
//         return playerfield;
//     }
//     private static int[] add_to_array(int[] array, int element){
//         int[] new_array = new int[array.length+1];
//         for (int i = 0; i < array.length; i++) {
//             new_array[i] = array[i];
//         }
//         new_array[new_array.length -1] = element;
//         return new_array;
//     }

//     private static int[] remove_array_from_array(int[] array1, int[] array2) {
//         int result_length = array1.length - array2.length;
//         int[] result = new int[result_length];
//         Arrays.sort(array1); 
//         Arrays.sort(array2); 
//         int j = 0;
//         for(int i = 0; i < array1.length; i++) {
//             if (array1[i] != array2[j]) {
//                 result[i - j] = array1[i];
//             } else {
//                 j++;
//             }
            
//         }
//         return result;
//     }

//     private static int sumSlice(int[] array, int start, int end) {
//         int sum = 0;
//         for (int i = start; i < end; i++) {
//             sum += array[i];
//         }
//         return sum;
//     }
}
