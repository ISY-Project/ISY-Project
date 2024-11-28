package org.bitshifters.games.stratego;

import java.util.HashMap;

import org.bitshifters.enums.Pawns;

public class UnitSet {
    private HashMap<Pawns, Integer> units = new HashMap<>();

    public HashMap<Pawns, Integer> getUnits() {
        return units;
    }

    public UnitSet() {
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
    }

    public boolean validate() {
        int total = 0;
        for (int i : units.values()) {
            total += i;
        }
        return total <= 40;
    }
}
