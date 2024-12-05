package org.bitshifters.games.stratego;

import java.util.HashMap;

public class UnitSet {
    private final HashMap<Pawns, Integer> unitsTen = new HashMap<>();
    private final HashMap<Pawns, Integer> unitsEight = new HashMap<>();

    public HashMap<Pawns, Integer> getUnitsTen() {
        return unitsTen;
    }

    public HashMap<Pawns, Integer> getUnitsEight() {
        return unitsEight;
    }

    public UnitSet() {
        unitsTen.put(Pawns.MARSHAL, 1);
        unitsTen.put(Pawns.GENERAL, 1);
        unitsTen.put(Pawns.COLONEL, 2);
        unitsTen.put(Pawns.MAJOR, 3);
        unitsTen.put(Pawns.CAPTAIN, 4);
        unitsTen.put(Pawns.LIEUTENANT, 4);
        unitsTen.put(Pawns.SERGEANT, 4);
        unitsTen.put(Pawns.MINER, 5);
        unitsTen.put(Pawns.SCOUT, 8);
        unitsTen.put(Pawns.SPY, 1);
        unitsTen.put(Pawns.BOMB, 6);
        unitsTen.put(Pawns.FLAG, 1);

        unitsEight.put(Pawns.MARSHAL, 1);
        unitsEight.put(Pawns.GENERAL, 1);

        unitsEight.put(Pawns.MINER, 2);
        unitsEight.put(Pawns.SCOUT, 2);
        unitsEight.put(Pawns.SPY, 1);
        unitsEight.put(Pawns.BOMB, 2);

        unitsEight.put(Pawns.FLAG, 1);
    }

    public boolean validate() {
        int total = 0;
        for (int i : unitsTen.values()) {
            total += i;
        }
        for (int i : unitsEight.values()) {
            total += i;
        }
        return total <= 50;
    }
}
