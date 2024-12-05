package org.bitshifters.games.stratego;

import java.util.HashMap;

public class UnitSet {
    private final HashMap<Pawns, Integer> units = new HashMap<>();

    public HashMap<Pawns, Integer> getUnits() {
        return units;
    }

    public UnitSet() {
    }

    public UnitSet setTenUnits() {
        units.clear();
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
        return this;
    }

    public UnitSet setEightUnits() {
        units.clear();
        units.put(Pawns.MARSHAL, 1);
        units.put(Pawns.GENERAL, 1);
        units.put(Pawns.MINER, 2);
        units.put(Pawns.SCOUT, 2);
        units.put(Pawns.SPY, 1);
        units.put(Pawns.BOMB, 2);
        units.put(Pawns.FLAG, 1);
        return this;
    }

    public boolean validate() {
        int total = 0;
        for (int i : units.values()) {
            total += i;
        }
        return total <= 40; // 40 is the total number of units in the game for the 10x10-unit set
    }
}
