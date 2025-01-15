package org.bitshifters.games.stratego;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a set of units for a game of Stratego.
 */
public class UnitSet {
    public static final UnitSet TEN = new UnitSet().setTenUnits();
    public static final UnitSet EIGHT = new UnitSet().setEightUnits();
    private final Map<Pawns, Integer> units = new LinkedHashMap<>();
    private int maxUnits = 40;

    /**
     * Get the units
     * @return A hashmap of the units and there counts
     */
    public Map<Pawns, Integer> getUnits() {
        return units;
    }

    /**
     * Set the units
     * @param units the units to set
     */
    public UnitSet() {
    }

    /**
     * Set the units for a 10x10 game
     */
    public UnitSet setTenUnits() {
        units.clear();
        // The insert order is important for the UI to display the units in the correct order
        units.put(Pawns.MARSHAL, 1);
        units.put(Pawns.GENERAL, 1);
        units.put(Pawns.COLONEL, 2);
        units.put(Pawns.MAJOR, 3);
        units.put(Pawns.CAPTAIN, 4);
        units.put(Pawns.LIEUTENANT, 4);
        units.put(Pawns.SERGEANT, 4);
        units.put(Pawns.MINER, 5);
        units.put(Pawns.SCOUT, 8);
        units.put(Pawns.SPY, 1);
        units.put(Pawns.BOMB, 6);
        units.put(Pawns.FLAG, 1);
        this.maxUnits = 40;
        return this;
    }

    /**
     * Set units for an 8x8 game
     */
    public UnitSet setEightUnits() {
        // The insert order is important for the UI to display the units in the correct order
        units.clear();
        units.put(Pawns.MARSHAL, 1);
        units.put(Pawns.GENERAL, 1);
        units.put(Pawns.MINER, 2);
        units.put(Pawns.SCOUT, 2);
        units.put(Pawns.SPY, 1);
        units.put(Pawns.BOMB, 2);
        units.put(Pawns.FLAG, 1);
        this.maxUnits = 10;
        return this;
    }

    /**
     * Validate the unit set
     * @return true if the unit set is valid, false otherwise
     */
    public boolean validate() {
        int total = 0;
        for (int i : units.values()) {
            total += i;
        }
        return total <= maxUnits;
    }

    /**
     * Get the total number of units
     * @return the total number of units
     */
    public int getTotalUnits() {
        int total = 0;
        for (int i : units.values()) {
            total += i;
        }
        return total;
    }
}
